<script>
	import ThemeManager from '$lib/components/admin/ThemeManager.svelte';
	import Header from '$lib/components/common/Header.svelte';
	import Navigation from '$lib/components/common/Navigation.svelte';
</script>

<div class="min-h-screen bg-gray-50">
	<Header siteName="Multipurpose CMS" />
	<Navigation />
	
	<main class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
		<ThemeManager />
	</main>
</div>
