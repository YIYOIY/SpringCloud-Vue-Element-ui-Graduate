<template>
  <div class="footer">


    <!-- 抽屉式菜单-->
    <div v-if="store.state.menu===0">
      <!-- 打开抽屉-->
      <!--        <el-affix  :offset="20">-->
      <el-affix position="bottom" :offset="10">
        <el-button type="text" style="color: #ffd04b;font-size: 20px;width: 100%;opacity: 0" @mouseover="drawer = true"
                   plain round>菜单
        </el-button>
      </el-affix>
      <!--        </el-affix>-->
      <el-drawer v-model="drawer" :show-close="false" direction="btt">
        <ElDivider content-position="center">Hi Yoi's BookShop! Awesome Books !</ElDivider>
        <FooterBarNavigation/>
      </el-drawer>
    </div>
    <h6>YoYi 游弋 2023 copright By yoi</h6>
  </div>
</template>
<script setup>
import store from "@/store";
import {ref} from "vue";
import FooterBarNavigation from "@/components/FooterBarNavigation.vue";

let drawer = ref(false)
</script>
<style scoped>
.footer {
  bottom: 0;
}

h6 {
  position: fixed;
  left: 45%;
  top: 93%;
  color: cadetblue;
}
</style>