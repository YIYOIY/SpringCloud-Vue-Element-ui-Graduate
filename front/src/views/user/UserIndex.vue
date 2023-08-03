<template>
  <div id="building">
    <el-form  label-position="top" ref="form" label-width="100" :model="user.user" :rules="rule" style="width: 100%">
      <div style="left: 10%;max-width: 15%;position: absolute">

      <el-form-item label="姓名" prop="userName">
        <el-input v-model="user.user.userName" disabled clearable placeholder="在此输入账户名称"></el-input>
      </el-form-item>

      <el-form-item label="性别">
        <el-select v-model="user.user.userSex" filterable clearable placeholder="选择性别" style="border-radius: 18px">
          <el-option label="男" value="男" />
          <el-option label="女" value="女" />
          <el-option label="保密" value="保密" />
        </el-select>
      </el-form-item>

      <el-form-item label="出生日期">
        <el-date-picker clearable v-model="user.user.userBirth"
          type="date" format="YYYY 年 MM 月 DD 日" placeholder="在此选择出生日期">
        </el-date-picker>
      </el-form-item>
      </div>

      <div style="right: 50%;max-width: 25%;position: absolute">
        <el-form-item label="账户密码" prop="userPassword">
          <el-input type="password" show-password v-model="user.user.userPassword" clearable  placeholder="在此输入账户密码"></el-input>
        </el-form-item>

        <el-form-item label="联系方式">
          <el-input v-model="user.user.userPhone"  clearable  placeholder="在此输入联系方式"></el-input>
        </el-form-item>
      </div>

      <div style="right: 10%;width: 25%;position: absolute">
        <el-form-item label="地址" prop="userAddress" @change="next">
          <el-input type="textarea" :autosize="{ minRows: 5, maxRows: 10 }" v-model="user.user.userAddress"
                    clearable placeholder="在此输入收货地址"></el-input>
        </el-form-item>
      </div>
    </el-form>

      <div style="right: 30%;top:75%;width:50%;position: absolute">
        <el-row class="control" gutter="40">
          <el-col :span="12">
            <el-button type="success"  @click="alter()" plain round>修改</el-button>
          </el-col>
          <el-col :span="12">
            <el-button type="danger"  @click="back()" plain round>注销</el-button>
          </el-col>
        </el-row>
      </div>
  </div>
</template>

<script setup>
import { ElMessage, ElNotification } from "element-plus";
import { useRouter } from "vue-router";
import { ref, reactive, onMounted } from "vue";
import { useStore } from "vuex";
import {alterUser, alterUserGet, deleteUser} from "@/api/UserApi";
let router = useRouter()
let store = useStore();

const user = reactive({
  user: {
    userName: '',
    userSex: '',
    userPassword: '',
    userBirth: '',
    userPhone: '',
    userAddress: '',
  }
})

console.log(user.user.userName)

onMounted(async () => {
  await alterUserGet(store.state.userId).then(Response => {
    user.user = Response.data
  })
})

const form = ref(null)

let alter = (() => {
  form.value.validate(valid => {
    if (!valid) {
      ElMessage.error("请输入信息符合要求!");
      return false;
    }
    let User = JSON.stringify(user.user)
    alterUser(User).then(Response => {
      ElNotification({
        message: Response.message,
        title: '修改成功！',
        type: 'success',
        Position: 'top-left'
      });
      router.push({
        name: 'user',
      })
    }).catch(Error => {
      ElNotification({
        message: Error.data.message + "  请重新输入!",
        title: '错误',
        type: 'error',
        Position: 'top-right'
      })
    })
  });
})

let rule = reactive({
  userAddress: [
    {
      required: false,
      message: "Please select Activity zone",
      trigger: "blur",
    },
    { min: 1, max: 100, message: "长度在 1 到 100 个字符", trigger: "blur" },
  ],
  userPassword: [
    { required: false, message: "请输入密码", trigger: "blur" },
    { min: 6, max: 25, message: "长度在 6 到 25 个字符", trigger: "blur" },
  ],
});

let back = (() => {
  if (confirm("确认注销?")) {
    deleteUser(store.state.userId).then(Response => {
      ElNotification({
        message: Response.message,
        title: '成功！',
        type: 'success',
        Position: 'top-left'
      });
      router.push({
        name: 'login'
      })
    }).catch(Error => {
      ElNotification({
        message: Error.data.message + "  请重新输入!",
        title: '错误',
        type: 'error',
        Position: 'top-right'
      })
      router.push({
        name: 'login'
      })
    })
  }
})
</script>

<style scoped>
#building {
  left: 0;
  background: url(../../assets/static/alterUser.jpg);
  width: 100%;
  height: 100%;
  font-size: large;
  position: fixed;
  background-size: 100% 100%;
  background-repeat: no-repeat;
  background-attachment: fixed;
  background-position: center;
  z-index: -1;
}

.el-button{
  width: 100%;
  height: 100%;
}

.el-input {
  --el-input-border-radius: 18px;
}


/deep/ .el-form-item__label {
  font-size: 20px;
  color: #fff;
}

/deep/ .el-step__description, el-step__title {
  font-size: 15px;
  color: #fff;
}

.el-form {
  font-size: 30px;
  margin: 6% 10%;
  width: 40%;
}

.control {
  top: 30%;
  left: 35%;
  width: 50%;
}
</style>
