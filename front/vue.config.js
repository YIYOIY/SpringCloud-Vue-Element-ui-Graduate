const {
    defineConfig
} = require('@vue/cli-service')
const AutoImport = require('unplugin-auto-import/webpack')
const Components = require('unplugin-vue-components/webpack')
const {
    ElementPlusResolver
} = require('unplugin-vue-components/resolvers')

module.exports = defineConfig({
    pwa: {
        iconPaths: {
            favicon32: 'favicon.ico',
            favicon16: 'favicon.ico',
            appleTouchIcon: 'favicon.ico',
            maskIcon: 'favicon.ico',
            msTileImage: 'favicon.ico'
        }
    },
    transpileDependencies: true,
    lintOnSave: false,
    publicPath: '/',
    devServer: {
        host: 'localhost',
        port: 80,
        open: true,
        proxy: {
            // 使用直连
            // '/excel': {
            // 	target: 'http://localhost:88',
            // 	changeOrigin: true,
            // 	pathRewrite: {
            // 		'^/excel': ''
            // 	},
            // 	bypass: function(req, res, proxyOptions) {
            // 		console.log(proxyOptions.target)
            // 	}
            // 使用直连,上传下载图片，获取图片地址
            '/picture': {
                target: 'http://localhost:88',
                pathRewrite: {
                    '^/picture': ''
                },
                changeOrigin: true,
                bypass: function (req, res, proxyOptions) {
                    console.log(proxyOptions.target)
                }
            },
            // 使用网关
            '/api': {
                target: 'http://localhost:89',
                changeOrigin: true,
                bypass: function (req, res, proxyOptions) {
                    console.log(proxyOptions.target)
                }
            },
            // 使用网关，根据图片地址查询图片
            '/img': {
                target: 'http://localhost:89',
                // 出现问题就用下面的直连
                // pathRewrite: {
                // 	'^/img': ''
                // },
                changeOrigin: true,
                bypass: function (req, res, proxyOptions) {
                    console.log(proxyOptions.target)
                }
            }
        },
    }
    ,
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