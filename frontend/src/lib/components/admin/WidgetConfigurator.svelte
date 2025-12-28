<script>
	import { onMount } from 'svelte';
	import { widgetsApi, pluginsApi } from '$lib/services/api.js';
	
	export let siteThemeConfigId = 1;
	
	let widgets = [];
	let availablePlugins = [];
	let widgetAreas = ['sidebar-primary', 'footer-1', 'footer-2', 'footer-3'];
	let loading = true;
	let error = null;
	let selectedArea = 'sidebar-primary';
	
	onMount(async () => {
		await loadWidgets();
		await loadAvailablePlugins();
	});
	
	async function loadWidgets() {
		try {
			loading = true;
			const response = await widgetsApi.getAll(siteThemeConfigId);
			widgets = response.data;
		} catch (err) {
			error = 'Failed to load widgets: ' + err.message;
		} finally {
			loading = false;
		}
	}
	
	async function loadAvailablePlugins() {
		try {
			const response = await pluginsApi.getActive();
			availablePlugins = response.data;
		} catch (err) {
			console.error('Failed to load plugins:', err);
		}
	}
	
	async function addWidget(pluginSlug) {
		try {
			await widgetsApi.create({
				siteThemeConfigId,
				pluginSlug,
				widgetAreaId: selectedArea,
				position: widgets.filter(w => w.widgetAreaId === selectedArea).length,
				settings: '{}',
				isActive: true
			});
			await loadWidgets();
		} catch (err) {
			alert('Failed to add widget: ' + err.message);
		}
	}
	
	async function deleteWidget(id) {
		if (!confirm('Remove this widget?')) return;
		try {
			await widgetsApi.delete(id);
			await loadWidgets();
		} catch (err) {
			alert('Failed to delete widget: ' + err.message);
		}
	}
	
	function getWidgetsByArea(areaId) {
		return widgets.filter(w => w.widgetAreaId === areaId).sort((a, b) => a.position - b.position);
	}
</script>

<div class="widget-configurator">
	<div class="mb-6">
		<h2 class="text-2xl font-bold text-gray-900">Widget Configurator</h2>
		<p class="text-gray-600 mt-2">Manage widgets in different areas of your site</p>
	</div>
	
	{#if loading}
		<div class="flex justify-center py-12">
			<div class="animate-spin rounded-full h-12 w-12 border-b-2 border-primary"></div>
		</div>
	{:else}
		<div class="grid grid-cols-1 md:grid-cols-3 gap-6">
			<!-- Widget Areas -->
			<div class="md:col-span-2">
				<div class="bg-white shadow-sm rounded-lg p-6">
					<h3 class="text-lg font-semibold mb-4">Widget Areas</h3>
					<div class="space-y-6">
						{#each widgetAreas as area}
							<div class="border rounded-lg p-4">
								<h4 class="font-medium text-gray-900 mb-3 capitalize">
									{area.replace('-', ' ')}
								</h4>
								<div class="space-y-2">
									{#each getWidgetsByArea(area) as widget}
										<div class="flex items-center justify-between bg-gray-50 p-3 rounded">
											<span class="text-sm">{widget.pluginSlug}</span>
											<button 
												on:click={() => deleteWidget(widget.id)}
												class="text-red-600 hover:text-red-800 text-sm"
											>
												Remove
											</button>
										</div>
									{:else}
										<div class="text-center text-gray-400 text-sm py-4">
											No widgets in this area
										</div>
									{/each}
								</div>
							</div>
						{/each}
					</div>
				</div>
			</div>
			
			<!-- Available Widgets -->
			<div>
				<div class="bg-white shadow-sm rounded-lg p-6">
					<h3 class="text-lg font-semibold mb-4">Available Widgets</h3>
					
					<div class="mb-4">
						<label class="block text-sm font-medium text-gray-700 mb-2">
							Add to Area
						</label>
						<select 
							bind:value={selectedArea}
							class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary"
						>
							{#each widgetAreas as area}
								<option value={area}>{area.replace('-', ' ')}</option>
							{/each}
						</select>
					</div>
					
					<div class="space-y-2">
						{#each availablePlugins as plugin}
							<button 
								on:click={() => addWidget(plugin.slug)}
								class="w-full text-left px-4 py-3 bg-primary-50 hover:bg-primary-100 rounded-md transition-colors"
							>
								<div class="font-medium text-sm">{plugin.name}</div>
								<div class="text-xs text-gray-600">{plugin.category}</div>
							</button>
						{:else}
							<div class="text-center text-gray-400 text-sm py-4">
								No active plugins available
							</div>
						{/each}
					</div>
				</div>
			</div>
		</div>
	{/if}
</div>
