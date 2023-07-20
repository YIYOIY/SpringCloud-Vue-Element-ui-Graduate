const {
	defineConfig
} = require('@vue/cli-service')
const AutoImport = require('unplugin-auto-import/webpack')
const Components = require('unplugin-vue-components/webpack')
const {
	ElementPlusResolver
} = require('unplugin-vue-components/resolvers')
const {
	resolve
} = require("@babel/core/lib/vendor/import-meta-resolve");

module.exports = defineConfig({
	transpileDependencies: true,
	lintOnSave: false,
	publicPath: '/',
	devServer: {
		host: 'localhost',
		port: 9090,
		open: true,
		proxy: {
			// 使用本地
			// '/api': {
			// 	target: 'http://localhost:87',
			// 	changeOrigin: true,
			// 	pathRewrite: {
			// 		'^/api': ''
			// 	},
			// 	bypass: function(req, res, proxyOptions) {
			// 		console.log(proxyOptions.target)
			// 	}
			// },
			// 使用直连
			'/excel': {
				target: 'http://localhost:88',
				changeOrigin: true,
				pathRewrite: {
					'^/excel': ''
				},
				bypass: function(req, res, proxyOptions) {
					console.log(proxyOptions.target)
				}
			},
			// 使用直连
			'/pict': {
				target: 'http://localhost:88',
				pathRewrite: {
					'^/pict': ''
				},
				changeOrigin: true,
				bypass: function(req, res, proxyOptions) {
					console.log(proxyOptions.target)
				}
			},
			// 使用网关
			'/api': {
				target: 'http://localhost:89',
				changeOrigin: true,
				bypass: function(req, res, proxyOptions) {
					console.log(proxyOptions.target)
				}
			},
			// 使用网关
			'/img': {
				target: 'http://localhost:89',
				changeOrigin: true,
				bypass: function(req, res, proxyOptions) {
					console.log(proxyOptions.target)
				}
			}
		}
	},
	configureWebpack: {
		plugins: [
			AutoImport({
				resolvers: [ElementPlusResolver()],
			}),
			Components({
				resolvers: [ElementPlusResolver()]
			})
		]
	}
})