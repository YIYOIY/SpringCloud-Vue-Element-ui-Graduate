import httpService from './request'
const http = {
    get(url, params) {
        const config = {
            method: 'get',
            url: url
        }
        if (params) config.params = params
        return httpService(config)
    },
    post(url, params) {
        const config = {
            method: 'post',
            url: url
        }
        if (params) config.data = params
        return httpService(config)
    },
    put(url, params) {
        const config = {
            method: 'put',
            url: url
        }
        if (params) config.data = params
        return httpService(config)
    },
    delete(url, params) {
        const config = {
            method: 'delete',
            url: url
        }
        if (params) config.params = params
        return httpService(config)
    }
}
export default http