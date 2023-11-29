<template>
  <div id="app">
    <SiaManager />
  </div>
</template>

<script>
import SiaManager from "@/components/SiaManager.vue";

export default {
  name: "App",
  components: { SiaManager, },
};
</script>
 
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
