package xyz.cssxsh.mirai.screenshot

import kotlinx.coroutines.*
import net.mamoe.mirai.console.command.CommandSender.Companion.toCommandSender
import net.mamoe.mirai.console.permission.PermissionService.Companion.hasPermission
import net.mamoe.mirai.console.plugin.jvm.*
import net.mamoe.mirai.contact.*
import net.mamoe.mirai.contact.Contact.Companion.uploadImage
import net.mamoe.mirai.event.*
import net.mamoe.mirai.utils.*
import org.openqa.selenium.*
import xyz.cssxsh.selenium.*

public object WebScreenshotHelper : KotlinPlugin(
    JvmPluginDescription(
        id = "xyz.cssxsh.mirai.plugin.web-screenshot-helper",
        name = "web-screenshot-helper",
        version = "0.1.0",
    ) {
        author("cssxsh")

        dependsOn("xyz.cssxsh.mirai.plugin.mirai-selenium-plugin", ">= 2.1.0")
    }
) {
    override fun onEnable() {
        WebScreenshotConfig.reload()
        resolveConfigFile("script").mkdirs()
        resolveConfigFile("script/www.example.com.js").writeText("""return document.body;""")

        globalEventChannel().subscribeMessages {
            """(?i)(?:截图|screenshot)\s+((https://|http://)\S+)""".toRegex() findingReply reply@{ match ->
                if (toCommandSender().hasPermission(parentPermission).not()) return@reply null
                val (urlString) = match.destructured
                val url = java.net.URL(urlString)
                val script = resolveConfigFile("script/${url.host}.js")
                if (script.exists().not()) {
                    logger.warning { "No Found $script" }
                    if ((sender as? NormalMember)?.isOperator() != true) {
                        return@reply null
                    }
                    script.writeText("return document.body;")

                }
                val temp = useRemoteWebDriver(config = WebScreenshotConfig) { driver ->
                    driver.get(urlString)
                    logger.info { "Screenshot For $urlString" }
                    val element = driver.executeScript(script.readText()) as? TakesScreenshot ?: driver
                    element.getScreenshotAs(OutputType.FILE)
                }
                val screenshot = resolveDataFile("${url.host}-${System.currentTimeMillis()}.png")
                temp.renameTo(screenshot)
                subject.uploadImage(screenshot)
            }
        }
    }

    override fun onDisable() {
        coroutineContext.cancelChildren()
    }
}
