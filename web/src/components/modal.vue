<template>
  <transition name="modal" id="modal-template">
    <div class="c-modal-mask">
      <div class="c-modal-wrapper">
        <div class="c-modal-container">
          <div class="c-modal-header">
            <slot name="header">
              default header
            </slot>
          </div>
          <div class="c-modal-body">
            <slot name="body">
              default body
            </slot>
          </div>
          <div class="c-modal-footer">
            <slot name="footer">
              <button class="btn btn-primary" @click="$emit('close')">Close</button>
            </slot>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
  export default {}
</script>

<style>
</style>
