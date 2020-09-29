<template>

  <form class="card auth-card">
    <div class="card-content">
      <span class="card-title"><i class="material-icons left">supervisor_account</i>IGS Crypto</span>
      <div class="input-field">
        <input
            id="login"
            type="text"
        >
        <label for="login">Login</label>
        <small class="helper-text invalid">Login</small>
      </div>
      <div class="input-field">
        <input
            id="email"
            type="text"
        >
        <label for="email">Email</label>
        <small class="helper-text invalid">Email</small>
      </div>
      <div class="input-field">
        <input
            id="password"
            type="password"
            class="validate"
        >
        <label for="password">Password</label>
        <small class="helper-text invalid">Password</small>
      </div>

      <p>
        <label>
          <input type="checkbox" />
          <span>Agreements</span>
        </label>
      </p>
    </div>
    <div class="card-action">
      <div>
        <button
            class="btn waves-effect waves-light auth-submit"
            type="submit"
        >
          Create account
          <i class="material-icons right">send</i>
        </button>
      </div>

      <p class="center">
        Have account?
        <router-link to="/login">Sign in!</router-link>
      </p>
    </div>
  </form>

</template>
