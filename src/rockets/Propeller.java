package rockets;

class Propeller implements Runnable,Comparable<Propeller>   {
	
		double maxPower;
		double currentPower;
		double desiredPower;
		String name;
		
		synchronized public void run(){ 
			
			System.out.println(this.name + " activado");
			if (this.currentPower<this.desiredPower) {
				powerUp();
				}
			else powerDown();
			System.out.println("Orden finalizada. Motor " + this.name + " actualmente a " + this.currentPower);

			}
		
			
		public Propeller (String name,int maxPower) {
			
			this.name=name;
			this.maxPower=maxPower;
			this.currentPower=0;
			this.desiredPower=0;
	
		}
		
		private void powerUp(){
			
			currentPower=this.desiredPower;			
		}
		
	
		private void powerDown() {
			
			currentPower=this.desiredPower;
			
		}
		
		@Override
	    public int compareTo(Propeller prop) {
			 if(this.maxPower<prop.maxPower)
		          return -1;
		    else if(prop.maxPower<this.maxPower)
		          return 1;
		    return 0;
	    }
		
		

}
