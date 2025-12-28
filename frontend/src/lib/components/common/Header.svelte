<script>
	export let siteName = 'CMS';
</script>

<header class="bg-white shadow">
	<div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
		<div class="flex justify-between h-16">
			<div class="flex">
				<div class="flex-shrink-0 flex items-center">
					<a href="/" class="text-2xl font-bold text-primary">
						{siteName}
					</a>
				</div>
			</div>
			<div class="flex items-center">
				<a 
					href="/admin/themes" 
					class="ml-4 px-4 py-2 text-sm font-medium text-gray-700 hover:text-primary transition-colors"
				>
					Admin
				</a>
			</div>
		</div>
	</div>
</header>
