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
    post(url, data) {
        const config = {
            method: 'post',
            url: url
        }
        if (data) config.data = data
        return httpService(config)
    },
    put(url, data) {
        const config = {
            method: 'put',
            url: url
        }
        if (data) config.data = data
        return httpService(config)
    },
    delete(url, data) {
        const config = {
            method: 'delete',
            url: url
        }
        if (data) config.data = data
        return httpService(config)
    }
}
export default http