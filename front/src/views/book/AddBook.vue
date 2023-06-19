<template>
  <div style="margin: 5% 2%">

    <el-form size="default" label-position="left" ref="form" width="100%" :model="book.book" :rules="rule">
      <div style="width: 50%;float: left">
        <el-row :gutter="2" justify="space-evenly">
          <el-col :span="10">
            <el-form-item label="编号">
              <el-input v-model="book.book.bookId" disabled>自动生成</el-input>
            </el-form-item>
            <el-form-item label="书名" prop="bookName">
              <el-input v-model="book.book.bookName"></el-input>
            </el-form-item>
            <el-form-item label="作者">
              <el-input v-model="book.book.bookAuthor"></el-input>
            </el-form-item>
            <el-form-item label="价格">
              <el-input v-model="book.book.bookPrice"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="系列" prop="seriesName">
              <el-select v-model="book.book.seriesName" placeholder="请选择">
                <el-option v-for="item in series" :key="item.seriesId" :label="item.seriesName"
                  :value="item.seriesName"></el-option>
              </el-select>
              <!-- <el-input v-model="book.book.seriesName" ></el-input> -->
            </el-form-item>
            <el-form-item label="出版社">
              <el-input v-model="book.book.bookFactory"></el-input>
            </el-form-item>
            <el-form-item label="发售日期">
              <el-date-picker v-model="book.book.bookAddDate" type="date" placeholder="选择日期 " format="YYYY/MM/DD"
                value-format="YYYY-MM-DD" />
            </el-form-item>

            <el-form-item label="上架数量" prop="bookNum">
              <el-input v-model="book.book.bookNum"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-collapse accordion>
          <el-collapse-item title="简介" name="1" style="width:90%;padding-left:4%">
            <el-input type="textarea" v-model="book.book.bookInfo" prop="book.book.bookInfo" clearable
              :autosize="{ minRows: 2, maxRows: 4 }" maxlength="100" placeholder="Please input"
              show-word-limit></el-input>
          </el-collapse-item>
          <el-collapse-item title="详情" name="2" style="width:90%;padding-left:4%">
            <el-input type="textarea" v-model="book.book.bookDetail" prop="book.book.Detail"
              :autosize="{ minRows: 2, maxRows: 4 }" clearable maxlength="1000" placeholder="Please input"
              show-word-limit></el-input>
          </el-collapse-item>

        </el-collapse>

        <el-row :gutter="2" justify="space-around" style="margin: 2% 1%">
          <el-col :offset="3" :span="8">
            <el-button type="danger" @click="add()">添加</el-button>
          </el-col>
          <el-col :span="8" :offset="3">
            <el-button type="success" @click="back()">返回</el-button>
          </el-col>
        </el-row>

      </div>

      <div style="width: 40%;float: right;margin-right: 1%">
        <img :src='PICTURE' style="width: 100%; height: 100%" v-show="havePicture"
          alt="当前后端未设置项目的图片文件路径在配置文件中，所以无法看到回显，但数据保存成功,重新启动项目即可看到书籍图片在书籍列表中" />
        <el-button type="warning" v-show="havePicture"><a href="api/test/download">下载图片</a></el-button>
        <el-button @click="havePicture = !havePicture" type="primary" v-show="havePicture">清空图片</el-button>
        <el-button @click="havePicture = !havePicture" type="primary" v-show="havePicture">从excel中导入书籍</el-button>

        <el-upload class=" upload-demo" drag action="api/importExcel" multiple :limit="1" encytype="multipart/form-data"
          name="photo" v-show="!havePicture">
          <el-icon class="el-icon--upload"><upload-filled /></el-icon>
          <div class="el-upload__text">
            从一张excel表中导入书籍
          </div>
        </el-upload>
        <el-divider></el-divider>

        <!-- <el-upload class=" upload-demo" drag action="api/test/up" multiple limit="100" encytype="multipart/form-data"
          ref="pict" name="photo" v-show="!havePicture" @keydown.y="handleBookPicture()">
          <el-icon class="el-icon--upload"><upload-filled /></el-icon>
          <div class="el-upload__text">
            <em>图片拖入</em> 或<em>点击上传</em>
            <p> 确定后点击 Y 键可查看上传的图片</p>
            重新上传图片请先清空列表
          </div>
          <template #tip>
            <div class="el-upload__tip">
              只能上传图片
            </div>
          </template>
        </el-upload> -->



        <el-upload ref="pict" class="upload-demo" action="api/test/up" multiple :limit="1" encytype="multipart/form-data"
          name="photo" v-show="!havePicture" :auto-upload="false">
          <template #trigger>
            <el-button type="primary">选择要上传的图片，限制一张</el-button>
          </template>
          <el-row :gutter="2" justify="space-around" style="margin: 2% 1%">
            <el-col :offset="3" :span="8">
              <el-button class="ml-3" type="success" @click="handleBookPicture">
                点击即可上传服务器
              </el-button>
            </el-col>
          </el-row>
          <template #tip>
            <div class="el-upload__tip">
              jpg/png files with a size less than 500kb
            </div>
          </template>
        </el-upload>
      </div>
    </el-form>

  </div>
</template>

<script setup>

import { useRouter } from "vue-router";
import { reactive, ref, onMounted } from "vue";
import { ElForm, ElFormItem, ElInput, ElButton, ElDatePicker, ElUpload, ElIcon, ElNotification } from "element-plus";
import axios from "axios";
let router = useRouter()


const book = reactive({
  book: {
    bookName: "",
    bookAddDate: "",
    bookAuthor: "",
    bookFactory: "",
    bookId: "",
    bookNum: "",
    bookPicture: "",
    bookPrice: "",
    seriesName: "",
    bookDetail: "",
    bookInfo: "",
  },
});

let series = ref('');


onMounted(() => {
  axios.get('api/series').then(Response => {
    series.value = Response.data
    console.log(series.value)
  }).catch(Error => {
    alert(Error.message)
  })
})



let PICTURE = ref('');
let havePicture = ref(false);

let pict = ref('')
let handleBookPicture = (() => {
  pict.value.submit()
  pict.value.clearFiles()
  setTimeout(() => {
    axios.get('api/getPicture').then(Response => {
      console.log(Response.data + "后端返回的值")
      if (Response.data != null && Response.data != "") {
        //对后端返回值验证后进行赋值
        PICTURE.value = Response.data
        book.book.bookPicture = Response.data
        // 显示图片页面
        console.log(PICTURE.value + "用于页面回显的地址")
        console.log(book.book.bookPicture + "数据库保存的图片地址")
        havePicture.value = true
      }
    }).catch(Error => {
      alert(Error.message)
    })
  }, 100);
})

let form = ref('')
const rule = reactive({
  bookName: [{
    required: true,
    message: '请输入书籍名称',
    trigger: 'blur'
  }],
  seriesName: [{
    required: true,
    message: '请输入书籍所属系列',
    trigger: 'blur'
  }],
  bookNum: [{
    required: true,
    message: '请输入书籍数量',
    trigger: 'blur'
  }],
})

let add = (() => {
  form.value.validate(valid => {
    if (!valid) {
      ElNotification({
        title: '警告',
        message: '请检查输入的内容是否正确',
        type: 'warning',
        position: 'top-left'
      })
      return false
    }
    let addBook = JSON.stringify(book.book)
    console.log(addBook)
    axios.post('api/book', addBook, { headers: { 'Content-Type': 'application/json' } }).then(Response => {
      let message = Response.data
      if (confirm(message + "!  是否跳转到书籍页")) {
        router.push({
          name: 'adminBooks',
        })
      }
    }).catch(Error => {
      alert(Error.message)
    })
  })
})


let back = (() => {
  router.push({
    name: 'adminBooks'
  })
})

</script>


<style scoped>
.el-radio-group {
  margin-right: 12px;
}

.el-input {
  width: 80%;
}

.el-form {
  margin-left: 70%;
  float: right;
  width: 100%;
}
</style>
