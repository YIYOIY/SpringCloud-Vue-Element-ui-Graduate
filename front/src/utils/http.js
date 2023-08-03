// axios封装的请求，不用每个方法都写一遍了
import serviceAxios from './service'
const http = {
    get(url, params) {
        const config = {
            method: 'get',
            url: url
        }
        if (params) config.params = params
        return serviceAxios(config)
    },
    post(url, data) {
        const config = {
            method: 'post',
            url: url
        }
        if (data) config.data = data
        return serviceAxios(config)
    },
    put(url, data) {
        const config = {
            method: 'put',
            url: url
        }
        if (data) config.data = data
        return serviceAxios(config)
    },
    delete(url, data) {
        const config = {
            method: 'delete',
            url: url
        }
        if (data) config.data = data
        return serviceAxios(config)
    }
}
export default http