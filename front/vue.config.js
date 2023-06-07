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
			'/api': {
				target: 'http://localhost:8090/',
				changeOrigin: true,
				pathRewrite: {
					'^/api': ''
				},
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