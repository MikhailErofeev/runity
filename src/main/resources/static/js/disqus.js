/**
 * @author Mikhail Erofeev https://github.com/MikhailErofeev/
 * @since 26.04.14
 */
/* * * CONFIGURATION VARIABLES: EDIT BEFORE PASTING INTO YOUR WEBPAGE * * */
var disqus_shortname = 'runity'; // required: replace example with your forum shortname
var disqus_url = 'http://localhost:8080/employee/bg';

/* * * DON'T EDIT BELOW THIS LINE * * */
(function () {
    var dsq = document.createElement('script');
    dsq.type = 'text/javascript';
    dsq.async = false;
    dsq.src = '//' + disqus_shortname + '.disqus.com/embed.js';
    (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(dsq);
})();