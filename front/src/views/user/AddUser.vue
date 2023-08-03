<template>
  <div id="building">
    <el-form label-position="top" ref="form" label-width="100" :model="user.user" :rules="rule" style="width: 100%">
      <div style="left: 10%;max-width: 15%;position: absolute">
        <el-form-item label="账户名称"  prop="userName">
          <el-input v-model="user.user.userName" @change="next" clearable placeholder="在此输入账户名称"></el-input>
        </el-form-item>

        <el-form-item label="性别">
          <el-select v-model="user.user.userSex" filterable clearable placeholder="选择性别" @change="next">
            <el-option label="男" value="男"/>
            <el-option label="女" value="女"/>
            <el-option label="保密" value="保密"/>
          </el-select>
        </el-form-item>

        <el-form-item label="出生日期">
          <el-date-picker clearable v-model="user.user.userBirth"
                          type="date" format="YYYY 年 MM 月 DD 日" @change="next" placeholder="在此选择出生日期">
          </el-date-picker>
        </el-form-item>
      </div>

      <div style="right: 50%;max-width: 25%;position: absolute">

        <el-form-item label="账户密码" prop="userPassword">
          <el-input type="password" show-password v-model="user.user.userPassword" clearable @change="next" placeholder="在此输入账户密码"></el-input>
        </el-form-item>

        <el-form-item label="联系方式">
          <el-input v-model="user.user.userPhone"  clearable @change="next" placeholder="在此输入联系方式"></el-input>
        </el-form-item>
      </div>

      <div style="right: 10%;width: 25%;position: absolute">
        <el-form-item label="地址" prop="userAddress" @change="next">
          <el-input type="textarea" :autosize="{ minRows: 5, maxRows: 10 }" v-model="user.user.userAddress"
                    clearable placeholder="在此输入收货地址"></el-input>
        </el-form-item>
      </div>
    </el-form>

    <div style="right: 25%;top:50%;width:50%;position: absolute">
      <el-row :gutter="40" class="control">
        <el-col :span="12">
          <el-button type="success" size="large" @click="add()" plain round>注册</el-button>
        </el-col>
        <el-col :span="12">
          <el-button type="primary" size="large" @click="cancelAdd()" plain round>取消</el-button>
        </el-col>
      </el-row>
    </div>

    <div style="right: 15%;top:55%;width:70%;position: absolute">
      <el-steps :active="active" align-center finish-status="success" style="width: 100%;margin-top: 16%;margin-left:4%;padding:10px 10px;background-color: rgba(243,243,243,0.2);border-radius: 10px">
        <el-step title="Step 1" description="填写姓名"/>
        <el-step title="Step 2" description="输入密码"/>
        <el-step title="Step 3" description="根据需要填写其他信息"/>
        <el-step title="Step 4" description="注册！"/>
      </el-steps>
    </div>
  </div>
</template>

<script setup>
import {ref, reactive} from "vue";
import {useRouter} from "vue-router";
import {useRoute} from "vue-router";
import {ElMessage, ElNotification} from "element-plus";
import {addUser} from "@/api/UserApi";

let active = ref(0)
let next = (() => {
  active.value++
})
let router = useRouter()
let route = useRoute()

let isEnroll = ref(false)
if (route.query.enroll) {
  isEnroll.value = true
}
console.log(isEnroll.value)

const user = reactive({
  user: {
    userName: '',
    userSex: '保密',
    userPassword: '',
    userBirth: '',
    userPhone: '',
    userAddress: '',
  }
})

let rule = reactive({
  userName: [
    {required: true, message: "请输入姓名", trigger: "blur"},
    {min: 1, max: 20, message: "长度在 1 到 20 个字符", trigger: "blur"},
  ],
  userAddress: [
    {
      required: false,
      message: "Please select Activity zone",
      trigger: "blur",
    },
    {min: 1, max: 100, message: "长度在 1 到 100 个字符", trigger: "blur"},
  ],
  userPassword: [
    {required: true, message: "请输入密码", trigger: "blur"},
    {min: 6, max: 25, message: "长度在 6 到 25 个字符", trigger: "blur"},
  ],
});

const form = ref(null)
const add = (() => {
  form.value.validate(valid => {
    if (!valid) {
      ElMessage.error("请输入信息符合要求!");
      return false;
    }

    let User = JSON.stringify(user.user)
    console.log(User)
    addUser(User).then(Response => {
      if (isEnroll.value) {
        ElNotification({
          message: Response.message,
          title: '录入成功！',
          type: 'success',
          Position: 'top-left'
        });
        router.push('/login')
      } else {
          router.push('/adminUsers')
      }
    }).catch(Error => {
      ElNotification({
        message: Error.data.message + " 请重新输入!",
        title: '错误',
        type: 'error',
        Position: 'top-right'
      })
    })
  });
})

const cancelAdd = (() => {
  router.push('/adminUsers')
})

</script>

<style scoped>
#building {
  bottom: 0;
  top: 0;
  left: 0;
  background: url(../../assets/static/enroll.jpg);
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

/deep/ .el-form-item__label {
  font-size: 20px;
  color: #fff;
}

/deep/ .el-step__description {
  font-size: 15px;
  color: #fff;
}

.el-form {
  font-size: 30px;
  margin: 6% 10%;
  width: 40%;
}
.el-input{
  --el-input-border-radius: 18px;
}

/deep/.el-input__inner{
  border-radius: 18px;
  width: 100%;
}

.control {
  top: 5%;
  left: 35%;
  width: 50%;
}

</style>