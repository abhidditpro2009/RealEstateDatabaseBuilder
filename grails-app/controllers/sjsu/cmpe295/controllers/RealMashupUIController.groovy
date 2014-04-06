package sjsu.cmpe295.controllers

class RealMashupUIController {

	
	def index() {
		println("In class UIController/index()")
		//redirect(action : 'home')
		//render("Hello")
		render(view:"home")
	}
}
