
const toggleSidebar = () => {
	if($(".sidebar").is(":visible")){
		
		//true
		//to close the sidebar
		$(".sidebar").css("display","none");
		$(".content").css("margine-left" ,"0%");
	}
	else{
		
		//false 
		//to open the side bar
			$(".sidebar").css("display","block");
		$(".content").css("margine-left" ,"22%");
	}
	
	
};