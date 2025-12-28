<script>
	import { onMount } from 'svelte';
	import { pluginsApi } from '$lib/services/api.js';
	
	let plugins = [];
	let loading = true;
	let error = null;
	
	onMount(async () => {
		await loadPlugins();
	});
	
	async function loadPlugins() {
		try {
			loading = true;
			const response = await pluginsApi.getAll();
			plugins = response.data;
		} catch (err) {
			error = 'Failed to load plugins: ' + err.message;
		} finally {
			loading = false;
		}
	}
	
	async function installPlugin(slug) {
		try {
			await pluginsApi.install(slug);
			await loadPlugins();
			alert('Plugin installed successfully');
		} catch (err) {
			alert('Failed to install plugin: ' + err.message);
		}
	}
	
	async function toggleActivation(slug) {
		try {
			await pluginsApi.toggleActivation(slug);
			await loadPlugins();
		} catch (err) {
			alert('Failed to toggle plugin: ' + err.message);
		}
	}
	
	async function deletePlugin(slug) {
		if (!confirm('Are you sure you want to delete this plugin?')) {
			return;
		}
		try {
			await pluginsApi.delete(slug);
			await loadPlugins();
			alert('Plugin deleted successfully');
		} catch (err) {
			alert('Failed to delete plugin: ' + err.message);
		}
	}
</script>

<div class="plugin-manager">
	<div class="mb-6">
		<h2 class="text-2xl font-bold text-gray-900">Plugin Manager</h2>
		<p class="text-gray-600 mt-2">Extend functionality with plugins and widgets</p>
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
		<div class="bg-white shadow-sm rounded-lg overflow-hidden">
			<table class="min-w-full divide-y divide-gray-200">
				<thead class="bg-gray-50">
					<tr>
						<th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
							Name
						</th>
						<th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
							Category
						</th>
						<th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
							Version
						</th>
						<th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
							Status
						</th>
						<th class="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">
							Actions
						</th>
					</tr>
				</thead>
				<tbody class="bg-white divide-y divide-gray-200">
					{#each plugins as plugin}
						<tr>
							<td class="px-6 py-4 whitespace-nowrap">
								<div class="text-sm font-medium text-gray-900">{plugin.name}</div>
								<div class="text-sm text-gray-500">{plugin.description || 'No description'}</div>
							</td>
							<td class="px-6 py-4 whitespace-nowrap">
								<span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-blue-100 text-blue-800">
									{plugin.category || 'General'}
								</span>
							</td>
							<td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
								{plugin.version || 'N/A'}
							</td>
							<td class="px-6 py-4 whitespace-nowrap">
								{#if !plugin.isInstalled}
									<span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-gray-100 text-gray-800">
										Not Installed
									</span>
								{:else if plugin.isActive}
									<span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800">
										Active
									</span>
								{:else}
									<span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-yellow-100 text-yellow-800">
										Inactive
									</span>
								{/if}
							</td>
							<td class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
								<div class="flex justify-end gap-2">
									{#if !plugin.isInstalled}
										<button 
											on:click={() => installPlugin(plugin.slug)}
											class="text-primary hover:text-primary-700"
										>
											Install
										</button>
									{:else}
										<button 
											on:click={() => toggleActivation(plugin.slug)}
											class="text-blue-600 hover:text-blue-900"
										>
											{plugin.isActive ? 'Deactivate' : 'Activate'}
										</button>
										<button 
											on:click={() => deletePlugin(plugin.slug)}
											class="text-red-600 hover:text-red-900"
										>
											Delete
										</button>
									{/if}
								</div>
							</td>
						</tr>
					{/each}
				</tbody>
			</table>
			
			{#if plugins.length === 0}
				<div class="text-center py-12 text-gray-500">
					<p>No plugins available.</p>
				</div>
			{/if}
		</div>
	{/if}
</div>
