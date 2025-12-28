<script>
	export let page = {};
	export let onSave = () => {};
	export let onCancel = () => {};
	
	let formData = {
		siteId: 1,
		pageType: page.pageType || 'HOME',
		slug: page.slug || '',
		title: page.title || '',
		content: page.content || '',
		layout: page.layout || 'default',
		isPublished: page.isPublished || false,
		seo: page.seo || {}
	};
	
	function handleSubmit() {
		onSave(formData);
	}
</script>

<div class="page-editor bg-white shadow-sm rounded-lg p-6">
	<h3 class="text-lg font-semibold text-gray-900 mb-4">
		{page.id ? 'Edit Page' : 'Create Page'}
	</h3>
	
	<form on:submit|preventDefault={handleSubmit} class="space-y-4">
		<div class="grid grid-cols-1 md:grid-cols-2 gap-4">
			<div>
				<label for="pageType" class="block text-sm font-medium text-gray-700 mb-1">
					Page Type
				</label>
				<select 
					id="pageType"
					bind:value={formData.pageType}
					class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary"
				>
					<option value="HOME">Home Page</option>
					<option value="BLOG">Blog Page</option>
					<option value="ABOUT">About Page</option>
					<option value="CONTACT">Contact Page</option>
				</select>
			</div>
			
			<div>
				<label for="slug" class="block text-sm font-medium text-gray-700 mb-1">
					Slug
				</label>
				<input 
					id="slug"
					type="text"
					bind:value={formData.slug}
					placeholder="page-slug"
					required
					class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary"
				/>
			</div>
		</div>
		
		<div>
			<label for="title" class="block text-sm font-medium text-gray-700 mb-1">
				Title
			</label>
			<input 
				id="title"
				type="text"
				bind:value={formData.title}
				placeholder="Page Title"
				required
				class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary"
			/>
		</div>
		
		<div>
			<label for="content" class="block text-sm font-medium text-gray-700 mb-1">
				Content
			</label>
			<textarea 
				id="content"
				bind:value={formData.content}
				rows="10"
				placeholder="Page content..."
				class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary"
			></textarea>
		</div>
		
		<div class="flex items-center">
			<input 
				id="isPublished"
				type="checkbox"
				bind:checked={formData.isPublished}
				class="h-4 w-4 text-primary focus:ring-primary border-gray-300 rounded"
			/>
			<label for="isPublished" class="ml-2 block text-sm text-gray-900">
				Publish this page
			</label>
		</div>
		
		<div class="flex gap-3 pt-4">
			<button 
				type="submit"
				class="px-6 py-2 bg-primary text-white rounded-md hover:bg-primary-600 transition-colors"
			>
				Save Page
			</button>
			<button 
				type="button"
				on:click={onCancel}
				class="px-6 py-2 bg-gray-200 text-gray-700 rounded-md hover:bg-gray-300 transition-colors"
			>
				Cancel
			</button>
		</div>
	</form>
</div>
