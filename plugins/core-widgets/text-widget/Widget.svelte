<script>
	export let settings = {
		title: 'Text Widget',
		content: 'Add your custom text or HTML here.'
	};
</script>

<div class="widget text-widget">
	{#if settings.title}
		<h3 class="widget-title">{settings.title}</h3>
	{/if}
	<div class="widget-content">
		{@html settings.content}
	</div>
</div>
