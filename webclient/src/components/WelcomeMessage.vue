<template>
  <section class="hero is-info is-fullheight">
    <div class="hero-body">
      <div class="container">
        <h1 class="title">
          ARGENT repartit gratuitement et efficacement notre thune
        </h1>
        <h2 class="subtitle">
          Argent allows you to share expenses easily
        </h2>
        <div>
          <router-link to="/create-group" class="button is-center">Create a group</router-link>
        </div>
      </div>
    </div>
  </section>
</template>
<script lang="ts">
  export default {name: 'WelcomeMessage'};
</script>
