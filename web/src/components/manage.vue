<template>
  <div>
    <div class="login">
      <template v-if="login">
        <span style="font-size: 20px">Hello, {{ username }}</span>
        <button class="btn btn-primary" @click="logout">注销</button>
      </template>
      <!--<a href="#" data-toggle="modal" data-target="#myModal">测试</a>-->
    </div>
    <h1 class="title">Your targets</h1>

    <div class="main">
      <div class="box">
        <div v-if="login">
          <template v-for="target in targets">
            <item :target="target"></item>
          </template>
        </div>
        <div class="logout" v-else>
          Welcome, new user!!
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {}
</script>
