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
    <el-form :size="size" :label-position="labelPosition" ref="form" label-width="auto" :model="admin">
      <el-form-item label="管理员名称">
        <el-input v-model="admin.admin.adminName" :model-value="admin.admin.adminName"></el-input>
      </el-form-item>
      <el-form-item label="管理员密码">
        <el-input v-model="admin.admin.adminPassword" show-password :model-value="admin.admin.adminPassword"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="success" plain round @click="add()">新增</el-button>
        <el-button type="primary" plain round @click="cancelAdd()">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { ref, reactive } from "vue";
import {addAdmin} from "@/api/AdminApi";
import { useRouter } from "vue-router";
import {ElMessage} from "element-plus";

const size = ref("default")
const labelPosition = ref("left")
let router = useRouter()
const admin = reactive({
  admin: {
    adminName: '',
    adminPassword: ''
  }
})

const add = (() => {
  let aAdmin = JSON.stringify(admin.admin)
    addAdmin(aAdmin).then(Response => {
      ElMessage.success(Response.message)
      router.push('/admin')
  }).catch(Error => {
    ElMessage.error(Error.data.message + "添加失败,请稍后重试!")
  })
})

const cancelAdd = (() => {
  router.push('/admin')
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