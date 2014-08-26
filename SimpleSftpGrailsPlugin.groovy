class SimpleSftpGrailsPlugin {
    // the plugin version
    def version = "0.2"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.2 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def title = "Simple Sftp Plugin" // Headline display name of the plugin
    def author = "perry dev"
    def authorEmail = "perrydevx@gmail.com"
    def description = '''\
        Simple way to connect and perform basic file actions to an SFTP server. Allows application to upload, download, delete, rename, create directory to an SFTP Server with one method call.
    '''

    def documentation = "https://github.com/perrydevx/simple-sftp"

    def license = "APACHE"

    def issueManagement = [ system: "GitHub", url: "https://github.com/perrydevx/simple-sftp/issues" ]

    def scm = [ url: "https://github.com/perrydevx/simple-sftp" ]
    
}
