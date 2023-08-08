<template>
  <div class="background">
    <el-button @click="add()" size="large" plain round type="success" style="top: 15.5%;left:40%;position: fixed">
      添加新用户
    </el-button>
    <el-button @click="search(searchName)" style="top: 16%;left: 25%;position: absolute" plain round type="info">
      查找用户
    </el-button>
    <el-button @click="search('all')" style="top: 16%;left: 32%;position: absolute" plain round type="primary">
      全部用户
    </el-button>
    <el-form>
      <el-form-item label="用户名称" prop="userName" style="top: 16%;left: 5%;position: absolute">
        <el-input type="text" v-model="searchName" autofocus maxlength="20" clearable
                  style="max-width: 200px" placeholder="输入用户名"></el-input>
      </el-form-item>
    </el-form>


    <el-table :data="users" :highlight-current-row="true" height="400" width="1000" style="margin-top: 13%"
              tooltip-effect="light">
      <el-table-column fixed prop="id" label="用户ID" :show-overflow-tooltip="true" sortable
                       width="150"></el-table-column>

      <el-table-column fixed prop="image.picture" label="头像" width="150">
        <template v-slot="scope">
          <el-image style="width: 100px; height: 100px;padding: 0 0 0 15%"
                    :src="scope.row.image==null?'img/未设置图片时的404.jpg':scope.row.image.picture" fit="cover"/>
        </template>
      </el-table-column>

      <el-table-column fixed prop="userName" class-name="userName" label="用户名" :show-overflow-tooltip="true"
                       width="150"/>
      <el-table-column fixed prop="userSex" label="性别" width="80"/>
      <el-table-column fixed prop="userMoney" label="账户余额" sortable width="150">
        <template v-slot="scope">
          RMB
          <el-tag effect="plain" type="warning" size="large">
            {{ scope.row.userMoney > 0 ? scope.row.userMoney : 0 }}
          </el-tag>
          元
        </template>
      </el-table-column>
      <el-table-column fixed prop="userPhone" label="联系方式" width="150"/>
      <el-table-column fixed prop="userSignTime" label="注册时间" :show-overflow-tooltip="true" width="200">
        <template v-slot="scope">
          {{ scope.row.userSignTime.substr(0, 10) + "  " + scope.row.userSignTime.substr(11, 13) }}
        </template>
      </el-table-column>
      <el-table-column fixed prop="userBirth" label="出生日期" sortable width="150"/>
      <el-table-column fixed prop="userAddress" label="收货地址" :show-overflow-tooltip="true" width="150"/>
      <el-table-column fixed prop="id" label="操作" width="220px">
        <template v-slot="scope">
          <el-button link round plain type="primary" @click="alter(scope.row.id)">详细信息</el-button>
          <el-button link round plain type="danger" @click="del(scope.row.id)">删除信息</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script setup>
import {reactive, ref} from "vue";
import {useRouter} from "vue-router";
import {deleteUser, adminGetUsers} from "@/api/UserApi";
import {useStore} from "vuex";
import {ElMessage, ElNotification} from "element-plus";

const store = useStore()
let router = useRouter()
let users = ref([])

let pageNo = ref(1)
let pageSize = ref(5)
let searchName = ref('')

let search = ((v) => {
  if (v === '') v = null
  adminGetUsers(store.state.adminId, pageNo.value, pageSize.value, v).then(Response => {
    users.value = Response.data.data
  }).catch((error)=>{
    if (error.data.code === 404) {
      ElNotification({
        title: '未注册',
        message: error.data.message,
        type: "error",
        position: "top-left",
        offset: 100,
      })
      users.value = null
    }
  })
})

  adminGetUsers(store.state.adminId, pageNo.value, pageSize.value, "all").then(Response => {
    // 因为又在统一返回上封装了分页，所以多了一层data
    users.value = Response.data.data
    console.log(users.value)
  })

  let alter = ((v) => {
    let id = ref(v)
    router.push({
      name: 'alterUser',
      query: {
        userId: id.value
      }
    })
  })

  const delUser = reactive({
    user: {
      id: ''
    }
  })

// 转换成json对象，后端才会解析成user对象
  let del = ((v) => {
    delUser.user.id = v
    if (confirm("确认删除?")) {
      deleteUser(JSON.stringify(delUser.user)).then(Response => {
        ElMessage.success(Response.message)
        adminGetUsers(store.state.adminId, pageNo.value, pageSize.value, "all").then(Response => {
          users.value = Response.data.data
        })
      }).catch(Error => {
        ElMessage.error(Error.data.message + "请稍后重试!")
      })
    }
  })

  let add = (() => {
    router.push({
      name: 'addUser'
    })
  })

</script>
<style scoped>
/deep/ .el-table__cell {
  font-size: 18px;
}

.background {
  margin: 0 3.5%;
}
</style>
