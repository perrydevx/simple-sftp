#simple-sftp Grails Plugin
Simple way to connect and perform basic file actions to an SFTP server

##Description
Allows application to upload, download, delete, rename, create directory to an SFTP Server with one method call. The connections and boilerplate codes are all handled by the plugin.

##Installation

##Dependency
Add dependency to “grails-app/conf/BuildConfig.groovy”

	dependencies {
		compile  'com.jcraft:jsch:0.1.49'
	}

##Config
Add config to “grails-app/conf/Config.groovy”
```
simpleSftp.server='qwerty.houston.com'
simpleSftp.username='helloworld’ 
simpleSftp.password='' // Leave empty string if you are using a private key, if password has a value it will overwrite the private key.
simpleSftp.remoteDir='/path_to_remote_dir/my_dir'
simpleSftp.port=22
simpleSftp.keyFilePath='/path_to_pk/my_pk.ppk'
simpleSftp.throwException=false // set to true if you want to handle the exceptions manually.
```

##Usage
Available methods
```
uploadFile(InputStream inputStream, String fileName)
downloadFile(String fileName)
removeFile(String fileName)
renameFile(String oldPath, String newPath)
createDir(String dirName)
```

##Sample code
Inject the service class, from there you can call the uploadFile(), downloadFile(), etc.
```
class MyController {
	ef simpleSftpService

	File file = File.createTempFile('testx', 'xml')
	file.write('test xml file content here')
	InputStream inputStream = new BufferedInputStream(new FileInputStream(file))

	simpleSftpService.uploadFile(inputStream, 'testx.xml')
}
```
