import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import store from "./store/index";
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

import App from './App.vue'
const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

import route from './router'
app.use(route).use(ElementPlus).use(store).mount('#app')
