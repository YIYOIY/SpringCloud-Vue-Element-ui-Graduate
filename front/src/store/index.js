import {createStore} from "vuex";

export default createStore({
    state: {
        // userId和adminId用于删除时验证是否是自身
        userId: '',
        userName: '',

        adminId: '',
        adminName: '',

        shopkeeperId: '',
        shopkeeperName: '',

        isAdmin: false,
        isUser: false,
        isShopkeeper: false,

        menu: 1,
    },
    mutations: {
        clearState(state) {
            state.adminName = ''
            state.adminId = ''

            state.userName = ''
            state.userId = ''

            state.shopkeeperId=''
            state.shopkeeperName=''

            state.isAdmin = false
            state.isUser = false
            state.isShopkeeper=false
            sessionStorage.removeItem('token')
            sessionStorage.removeItem('store')
        }
    }
})