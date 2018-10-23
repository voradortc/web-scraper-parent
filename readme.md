# **Web Scraper**

Small POC that receives one file argument (a path to a text file) which contains _one URL per line_; the content of the
URLs are parsed and searched for a set of tokens according to a set of REGEX's defined in the configuration.

Output is presented in a text file for each url, the name of the file will be that of the hostname of the URL, reason
why using different URLs from the same host will result in an error.

* _Example pattern can be found in the resources folder of the **web-scraper-flow** module_
* _This version does not overwrite output files_

## **Configuration**

A file called _web-scraper.properties_ which contains the following properties needs to be present in the classpath:

```
output.path=path/to/the/output/directory/
pattern.path=path/to/the/patterns/directory/
```

Also, in the patterns directory; for each pattern that you wish to search for, a text file needs to exist which contains
the REGEX that determines the pattern (in one line).

Uses:
* _[HtmlUnit](http://htmlunit.sourceforge.net)_
* _[Spring Boot](https://spring.io/projects/spring-boot)_
* _[Mockito](https://site.mockito.org/)_