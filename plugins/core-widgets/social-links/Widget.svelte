<script>
	export let settings = {
		title: 'Follow Us',
		facebook: '',
		twitter: '',
		instagram: '',
		linkedin: '',
		youtube: ''
	};
	
	const socialLinks = [
		{ name: 'Facebook', icon: '📘', key: 'facebook' },
		{ name: 'Twitter', icon: '🐦', key: 'twitter' },
		{ name: 'Instagram', icon: '📷', key: 'instagram' },
		{ name: 'LinkedIn', icon: '💼', key: 'linkedin' },
		{ name: 'YouTube', icon: '📺', key: 'youtube' }
	];
	
	function hasAnyLinks() {
		return socialLinks.some(social => settings[social.key]);
	}
</script>

<div class="widget social-links-widget">
	{#if settings.title}
		<h3 class="widget-title">{settings.title}</h3>
	{/if}
	{#if hasAnyLinks()}
		<div class="social-links">
			{#each socialLinks as social}
				{#if settings[social.key]}
					<a 
						href={settings[social.key]} 
						target="_blank" 
						rel="noopener noreferrer"
						class="social-link"
						title={social.name}
					>
						<span class="social-icon">{social.icon}</span>
						<span class="social-name">{social.name}</span>
					</a>
				{/if}
			{/each}
		</div>
	{:else}
		<p class="text-sm text-gray-500">No social links configured.</p>
	{/if}
</div>

<style>
	.social-links {
		display: flex;
		flex-wrap: wrap;
		gap: 0.75rem;
	}
	
	.social-link {
		display: flex;
		align-items: center;
		gap: 0.5rem;
		padding: 0.5rem 1rem;
		background: #f3f4f6;
		border-radius: 0.375rem;
		text-decoration: none;
		color: inherit;
		transition: all 0.2s;
	}
	
	.social-link:hover {
		background: #e5e7eb;
		transform: translateY(-2px);
	}
	
	.social-icon {
		font-size: 1.25rem;
	}
	
	.social-name {
		font-size: 0.875rem;
		font-weight: 500;
	}
</style>
