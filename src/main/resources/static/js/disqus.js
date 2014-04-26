/**
 * @author Mikhail Erofeev https://github.com/MikhailErofeev/
 * @since 26.04.14
 */
function disqusInit(absolute_url) {
    DISQUS.reset({
        reload: true,
        config: function () {
            this.page.url = absolute_url;
            this.page.identifier = "runity" + absolute_url;
        }
    });
}