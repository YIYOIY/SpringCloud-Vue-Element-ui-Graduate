<template>
  <div style="margin: 10% 2%;text-align: center;align-content: center">
    <div class="control">
      <p style="margin-left: 5%">size control</p>
      <el-radio-group v-model="size">
        <el-radio label="large">large</el-radio>
        <el-radio label="default">default</el-radio>
        <el-radio label="small">small</el-radio>
      </el-radio-group>
      <p style="margin-left: 5%">Position Control</p>
      <el-radio-group v-model="labelPosition">
        <el-radio label="left">left</el-radio>
        <el-radio label="right">right</el-radio>
        <el-radio label="top">top</el-radio>
      </el-radio-group>
    </div>

    <br />
    <el-form :size="size" :label-position="labelPosition" ref="form" label-width="auto" :model="admin">
      <el-form-item label="名称">
        <el-input v-model="admin.admin.adminName" prop="adminName"></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="admin.admin.adminPassword" show-password prop="adminPassword"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="danger" @click="alter()">修改</el-button>
        <el-button type="success" @click="back()">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";
import { ref, reactive, onMounted, toRef } from "vue";
import {alterAdmin,alterAdminGet} from "@/api/AdminApi";
import {ElMessage} from "element-plus";

let router = useRouter()

const prop = defineProps(['adminId'])
let id = toRef(prop, 'adminId')

const size = ref("default")
const labelPosition = ref("left")

const admin = reactive({
  admin: {
    adminName: '',
    adminPassword: ''
  }
})

console.log(admin.admin.adminName)
onMounted(async () => {
   alterAdminGet(id.value).then(Response => {
    admin.admin = Response.data
  })
})


let alter = (() => {
  let Admin = JSON.stringify(admin.admin)
  alterAdmin(Admin).then(Response => {
    let message = Response.message
    if (confirm(message + "!  是否跳转到管理员页")) {
      router.push({
        name: 'admin',
      })
    }
  }).catch(Error => {
    ElMessage.error(Error.data.message)
  })
})


let back = (() => {
  router.push({
    name: 'admin'
  })
})
</script>

<style scoped>
.el-radio-group {
  margin-right: 12px;
}

.el-input {
  width: 30%;
}

.control {
  float: left;
  width: 30%;
}

.el-form {
  margin-left: 10%;
  float: right;
  width: 60%;
}
</style>
