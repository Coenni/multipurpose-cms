<script>
	import { onMount } from 'svelte';
	import { pagesApi } from '$lib/services/api.js';
	import PageEditor from '$lib/components/admin/PageEditor.svelte';
	import Header from '$lib/components/common/Header.svelte';
	import Navigation from '$lib/components/common/Navigation.svelte';
	
	let pages = [];
	let loading = true;
	let showEditor = false;
	let editingPage = {};
	const siteId = 1;
	
	onMount(async () => {
		await loadPages();
	});
	
	async function loadPages() {
		try {
			loading = true;
			const response = await pagesApi.getAll(siteId);
			pages = response.data;
		} catch (err) {
			console.error('Failed to load pages:', err);
		} finally {
			loading = false;
		}
	}
	
	function createNewPage() {
		editingPage = {};
		showEditor = true;
	}
	
	function editPage(page) {
		editingPage = { ...page };
		showEditor = true;
	}
	
	async function savePage(pageData) {
		try {
			if (editingPage.id) {
				await pagesApi.update(editingPage.id, pageData);
			} else {
				await pagesApi.create(pageData);
			}
			showEditor = false;
			await loadPages();
			alert('Page saved successfully');
		} catch (err) {
			alert('Failed to save page: ' + err.message);
		}
	}
	
	async function deletePage(id) {
		if (!confirm('Are you sure you want to delete this page?')) return;
		try {
			await pagesApi.delete(id);
			await loadPages();
			alert('Page deleted successfully');
		} catch (err) {
			alert('Failed to delete page: ' + err.message);
		}
	}
	
	function cancelEdit() {
		showEditor = false;
		editingPage = {};
	}
</script>

<div class="min-h-screen bg-gray-50">
	<Header siteName="Multipurpose CMS" />
	<Navigation />
	
	<main class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
		<div class="mb-6 flex justify-between items-center">
			<div>
				<h2 class="text-2xl font-bold text-gray-900">Pages</h2>
				<p class="text-gray-600 mt-2">Manage your site's pages</p>
			</div>
			<button 
				on:click={createNewPage}
				class="px-6 py-2 bg-primary text-white rounded-md hover:bg-primary-600 transition-colors"
			>
				Create New Page
			</button>
		</div>
		
		{#if showEditor}
			<PageEditor page={editingPage} onSave={savePage} onCancel={cancelEdit} />
		{:else if loading}
			<div class="flex justify-center py-12">
				<div class="animate-spin rounded-full h-12 w-12 border-b-2 border-primary"></div>
			</div>
		{:else}
			<div class="bg-white shadow-sm rounded-lg overflow-hidden">
				<table class="min-w-full divide-y divide-gray-200">
					<thead class="bg-gray-50">
						<tr>
							<th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Title</th>
							<th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Slug</th>
							<th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Type</th>
							<th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Status</th>
							<th class="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase">Actions</th>
						</tr>
					</thead>
					<tbody class="divide-y divide-gray-200">
						{#each pages as page}
							<tr>
								<td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">{page.title}</td>
								<td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{page.slug}</td>
								<td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{page.pageType}</td>
								<td class="px-6 py-4 whitespace-nowrap">
									<span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full {page.isPublished ? 'bg-green-100 text-green-800' : 'bg-yellow-100 text-yellow-800'}">
										{page.isPublished ? 'Published' : 'Draft'}
									</span>
								</td>
								<td class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
									<button on:click={() => editPage(page)} class="text-primary hover:text-primary-700 mr-4">Edit</button>
									<button on:click={() => deletePage(page.id)} class="text-red-600 hover:text-red-900">Delete</button>
								</td>
							</tr>
						{:else}
							<tr>
								<td colspan="5" class="px-6 py-12 text-center text-gray-500">
									No pages found. Create your first page to get started.
								</td>
							</tr>
						{/each}
					</tbody>
				</table>
			</div>
		{/if}
	</main>
</div>
