	
	// 初始化编辑器属性
	var editor = UE.getEditor('container', {
		lang : "zh-cn",
		toolbars : [
				[ 'fullscreen', 'source', 'bold', 'italic', 'underline',
						'forecolor', 'strikethrough', 'forecolor', 'undo',
						'redo', 'insertorderedlist', 'insertunorderedlist',
						'fontsize', 'paragraph', 'insertcode', 'customstyle' ],
				[ 'indent', 'justifyleft', 'justifyright', 'justifycenter',
						'justifyjustify', 'link', 'unlink', 'inserttable',
						'blockquote', 'formatmatch', 'searchreplace', 'drafts',
						'preview' ] ],
		autoHeightEnabled : false,
		initialFrameWidth : '100%',
		initialFrameHeight : 400,
		enableAutoSave : true,
		saveInterval : 5000

	});
	// 编辑器加载完成后执行
	editor.ready(function() {
		if (!this.hasContents()) {
			//
			setTimeout(function() {
				editor.execCommand('drafts');
			}, 500);

		}

	});
