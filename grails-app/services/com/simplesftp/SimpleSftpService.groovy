package com.simplesftp

import com.jcraft.jsch.Channel
import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session

import grails.transaction.Transactional

@Transactional
class SimpleSftpService {
	def grailsApplication
	
	def uploadFile(InputStream inputStream, String fileName) {
		connect { ChannelSftp sftp ->
			sftp.put inputStream, fileName
		}
	}

	def downloadFile(String fileName) {
		connect({ ChannelSftp sftp ->
			File outputFile = File.createTempFile(fileName,'')
			outputFile?.newOutputStream() << sftp.get(fileName)
			outputFile
		}, false)
	}

	def removeFile(String fileName) throws Throwable {
		connect { ChannelSftp sftp ->
			sftp.rm fileName
		}
	}

	def renameFile(String oldPath, String newPath) {
		connect { ChannelSftp sftp ->
			sftp.rename oldPath, newPath
		}
	}
	
	def createDir(String dirName) {
		connect { ChannelSftp sftp ->
			sftp.mkdir dirName
		}
	}

	private def connect(Closure c, boolean disconnect = true) {
		Session session = null
		ChannelSftp sftp = null
		String server =  grailsApplication.config.simpleSftp.server
		String username = grailsApplication.config.simpleSftp.username
		String password = grailsApplication.config.simpleSftp.password
		String remoteDir = grailsApplication.config.simpleSftp.remoteDir
		Integer port = grailsApplication.config.simpleSftp.port.toInteger()
		String keyFilePath = grailsApplication.config.simpleSftp.keyFilePath
		Boolean throwException = grailsApplication.config.simpleSftp.throwException
		
		try {
			JSch jSch = new JSch()
			session = jSch.getSession username, server, port
			session.setConfig "StrictHostKeyChecking", "no"
			
			if (password) {
				session.password = password
			} else {
				File keyFile = new File(keyFilePath)
				jSch.addIdentity(keyFile?.absolutePath)
			}
			
			session.connect()
			Channel channel = session.openChannel "sftp"
			channel.connect()
			sftp = channel as ChannelSftp
			sftp.cd remoteDir
			c.call sftp
		} catch (Exception e) {
			log.error(e)
            if (throwException) {
                throw e
            }
		} finally {
			if (disconnect) {
				sftp?.exit()
				session?.disconnect()
			}
		}
	}
}
