<template>
  <div id="app">
    <nav class="navbar is-white" role="navigation" aria-label="main navigation">
      <div class="navbar-brand">
        <router-link to="/" class="navbar-item">ARGENT</router-link>
        <router-link active-class="is-active" to="/create-group" class="navbar-item">Create group</router-link>
        <router-link active-class="is-active" to="/my-groups" class="navbar-item">My groups</router-link>
      </div>
    </nav>
    <router-view/>
  </div>
</template>
