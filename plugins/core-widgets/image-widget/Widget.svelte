<script>
	export let settings = {
		title: '',
		imageUrl: 'https://via.placeholder.com/300x200',
		altText: 'Image',
		linkUrl: '',
		openInNewTab: false
	};
</script>

<div class="widget image-widget">
	{#if settings.title}
		<h3 class="widget-title">{settings.title}</h3>
	{/if}
	<div class="widget-content">
		{#if settings.linkUrl}
			<a 
				href={settings.linkUrl} 
				target={settings.openInNewTab ? '_blank' : '_self'}
				rel={settings.openInNewTab ? 'noopener noreferrer' : ''}
			>
				<img src={settings.imageUrl} alt={settings.altText} class="widget-image" />
			</a>
		{:else}
			<img src={settings.imageUrl} alt={settings.altText} class="widget-image" />
		{/if}
	</div>
</div>

<style>
	.widget-image {
		width: 100%;
		height: auto;
		display: block;
		border-radius: 0.375rem;
	}
</style>
