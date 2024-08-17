import org.w3c.dom.Element
import java.io.File
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory

val feedUrl = "https://letterboxd.com/zebrastuhl/rss/"
val rssFeed = URL(feedUrl).readText()
val document =
    DocumentBuilderFactory
        .newInstance()
        .newDocumentBuilder()
        .parse(rssFeed.byteInputStream())

document.documentElement.normalize()

val items = document.getElementsByTagName("item")
val htmlContent = StringBuilder()
htmlContent.append("<html><head><title>Letterboxd RSS Feed</title></head><body>")
htmlContent.append("<h1>Recent Movies from Letterboxd</h1>")
htmlContent.append("<table border='1'><tr><th>Title</th><th>Link</th><th>Published</th></tr>")

for (i in 0 until items.length) {
    val item = items.item(i) as Element
    val title = item.getElementsByTagName("title").item(0).textContent
    val link = item.getElementsByTagName("link").item(0).textContent
    val pubDate = item.getElementsByTagName("pubDate").item(0).textContent

    htmlContent.append("<tr><td>$title</td><td><a href='$link'>Link</a></td><td>$pubDate</td></tr>")
}

htmlContent.append("</table></body></html>")

// Speichern der HTML-Datei
val file = File("index.html")
file.writeText(htmlContent.toString())
