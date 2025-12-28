<script>
	import { onMount } from 'svelte';
	import { themesApi } from '$lib/services/api.js';
	
	let themes = [];
	let loading = true;
	let error = null;
	let activeTheme = null;
	
	onMount(async () => {
		await loadThemes();
		await loadActiveTheme();
	});
	
	async function loadThemes() {
		try {
			loading = true;
			const response = await themesApi.getAll();
			themes = response.data;
		} catch (err) {
			error = 'Failed to load themes: ' + err.message;
		} finally {
			loading = false;
		}
	}
	
	async function loadActiveTheme() {
		try {
			const response = await themesApi.getActive();
			activeTheme = response.data;
		} catch (err) {
			console.log('No active theme found');
		}
	}
	
	async function installTheme(slug) {
		try {
			await themesApi.install(slug);
			await loadThemes();
			alert('Theme installed successfully');
		} catch (err) {
			alert('Failed to install theme: ' + err.message);
		}
	}
	
	async function activateTheme(slug) {
		try {
			await themesApi.activate(slug);
			await loadThemes();
			await loadActiveTheme();
			alert('Theme activated successfully');
		} catch (err) {
			alert('Failed to activate theme: ' + err.message);
		}
	}
	
	async function deleteTheme(slug) {
		if (!confirm('Are you sure you want to delete this theme?')) {
			return;
		}
		try {
			await themesApi.delete(slug);
			await loadThemes();
			alert('Theme deleted successfully');
		} catch (err) {
			alert('Failed to delete theme: ' + err.message);
		}
	}
</script>

<div class="theme-manager">
	<div class="mb-6">
		<h2 class="text-2xl font-bold text-gray-900">Theme Manager</h2>
		<p class="text-gray-600 mt-2">Manage and customize your site's appearance</p>
	</div>
	
	{#if loading}
		<div class="flex justify-center py-12">
			<div class="animate-spin rounded-full h-12 w-12 border-b-2 border-primary"></div>
		</div>
	{:else if error}
		<div class="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded">
			{error}
		</div>
	{:else}
		<div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
			{#each themes as theme}
				<div class="bg-white border rounded-lg shadow-sm overflow-hidden">
					<div class="aspect-video bg-gray-200 relative">
						{#if theme.screenshotUrl}
							<img src={theme.screenshotUrl} alt={theme.name} class="w-full h-full object-cover" />
						{:else}
							<div class="flex items-center justify-center h-full text-gray-400">
								No Preview
							</div>
						{/if}
						{#if theme.isActive}
							<div class="absolute top-2 right-2 bg-green-500 text-white px-3 py-1 rounded-full text-sm font-semibold">
								Active
							</div>
						{/if}
					</div>
					<div class="p-4">
						<h3 class="text-lg font-semibold text-gray-900">{theme.name}</h3>
						<p class="text-sm text-gray-600 mt-1">{theme.description || 'No description'}</p>
						<div class="mt-2 text-xs text-gray-500">
							<span>Version: {theme.version || 'N/A'}</span>
							<span class="mx-2">•</span>
							<span>By: {theme.author || 'Unknown'}</span>
						</div>
						<div class="mt-4 flex gap-2">
							{#if !theme.isInstalled}
								<button 
									on:click={() => installTheme(theme.slug)}
									class="flex-1 bg-primary text-white px-4 py-2 rounded hover:bg-primary-600 transition-colors text-sm"
								>
									Install
								</button>
							{:else if !theme.isActive}
								<button 
									on:click={() => activateTheme(theme.slug)}
									class="flex-1 bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600 transition-colors text-sm"
								>
									Activate
								</button>
								<button 
									on:click={() => deleteTheme(theme.slug)}
									class="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600 transition-colors text-sm"
								>
									Delete
								</button>
							{:else}
								<div class="flex-1 bg-gray-100 text-gray-600 px-4 py-2 rounded text-center text-sm">
									Currently Active
								</div>
							{/if}
						</div>
					</div>
				</div>
			{/each}
		</div>
		
		{#if themes.length === 0}
			<div class="text-center py-12 text-gray-500">
				<p>No themes available. Add themes to get started.</p>
			</div>
		{/if}
	{/if}
</div>
