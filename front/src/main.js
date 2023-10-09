import { createApp } from 'vue'
import store from "./store/index";
import route from './router'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import {checkToken} from "@/api/LoginApi";

const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.use(route).use(ElementPlus).use(store).mount('#app')
setInterval(() => {
    let tokenVerfiy = sessionStorage.getItem('token');
    checkToken(tokenVerfiy).then((response) => {
            sessionStorage.setItem("token", response.data);
    })
},10000)