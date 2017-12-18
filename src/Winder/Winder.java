#include <AccelStepper.h>
#include <MultiStepper.h>
#include <Arduino.h>

	float man_diam;
	float man_speed;

	float pi = 3.14159265359;

	int man_origin;
	int man_length;

	float car_speed;
	int car_dest;
	int car_origin;

	int tot_layers;
	float tube_length;
	float wind_angle;
	float rad_angle;
	float tow_width;
	float tow_thickness;
	int current_Layer;
	float maxFeedRate = 500;

	int stepsManRev = 500;
	int stepsCarRev = 200;

	long mandrel_position = 0;
	long car_position = 0;

	boolean tubeValues = false;

	AccelStepper carriage(AccelStepper::DRIVER, 11, 10);
	AccelStepper mandrel(AccelStepper::DRIVER, 7, 6);

	void monInit();
	void printWarning();
	int menu();
	void configuration();
	int askInt(String thing);
	float askFloat(String thing);
	void printMon(String thing);
	boolean printQ (String thing);
	void printLogo();
	void build();
	void pass_oddLayer(boolean odd);
	void pass_evenLayer();
	void safetyCheck();
	void stopCheck();
	void RangeHome();
	int totPass();
	float manSpeedCalc();
	float carSpeedCalc();
	float curr_diam();
	float man_circ();
	float minMandrell_Length();
	long carDest();
	long manDest();
	int turnAroundOffset();
	int passOffset();


	void setup()
	{
		monInit();  // initiate serial monitor
		printMon("\n\n");
		printWarning();
	}

	void loop()
	{


		int User_selection = menu();
		while (User_selection < 0 || User_selection > 3)
		{
			User_selection = askInt("\n\tInvalid Selection.  Please choose a valid option (1-3): ");
			Serial.print(User_selection);
		}

		switch (User_selection)
		{
			case 1:
			    configuration();
			    break;

			case 2:
			    RangeHome();
			    break;

			case 3:
				if (printQ("\n\nAre you sure you would like begin winding a tube? (y/n): "))
				{
					if (tubeValues == true)
					{
						build();
					}

				}
				break;
		}
	}

	/* This is a library of Functions that allows a very easy to remember list
	 *  of wrappers for communicating with the operator via the serial monitor
	 *  of the Arduino IDE.  These functions simplify mulitple steps of IO code
	 *  into single command lines for use in the main loop.
	 */

	void monInit()
	{
		Serial.begin(9600);
	  	Serial.setTimeout(600000);
	    printLogo();
	}

	void printWarning()
	{
		printMon("\n\nWARNING: this software is still in development, and does NOT\n");
		printMon("guarantee that the machine knows how dumb you can be.  Some safety\n");
		printMon("features HAVE been implemented, but there are situations where you\n");
		printMon("can tell the machine to do things that WILL break it.\n\n");
		printMon("Be sure that all numbers you enter are correct and there shouldn't\n");
		printMon("any problems.\n");
	}

	int menu()
	{
		printMon("\n\n\tThank you for Choosing WinderOS\n\n\t");
		printMon("1. Set values for size of tube to be wound\n\t");
		printMon("2. Test Range and Home the winder. (this requires movement.  Please ensure machine is not obstructed)\n\t");
		printMon("3. Begin winding (if Emergency stop was performed, winder will begin where previously stopped\n\t");

		return askInt("Please input your selection:  ");
	}

	void configuration()
	{

		man_diam = askFloat("Please enter the outside diameter of the mandrel in millimeters (ex: 345.4): ");

		while (man_diam <= 0)
		{
			man_diam = askFloat("Please enter the outside diameter of the mandrel in millimeters (ex: 345.4): ");
		}

		tube_length = askFloat("Please enter the desired length of useable tube in meters \n\t(Mandrel must be minimum 0.2 meters (approx 8in) longer than desired tube to allow for trimming) : ");

		while (tube_length <= 0.2)
		{
			tube_length = askFloat("Please enter the desired length of useable tube in meters \n\t(Mandrel must be minimum 0.2 meters (approx 8in) longer than desired tube to allow for trimming) : ");
		}

		tube_length = tube_length * 1000;


		tot_layers = askInt("Please input number of desired layers: ");
		while (tot_layers <= 0)
		{
			tot_layers = askInt("Please input number of desired layers: ");
		}

		wind_angle = askFloat("Please enter the winding angle in degrees; ");
		rad_angle = ((wind_angle/180) * pi);

		tow_width = askFloat("Please enter the tow width in mm:  ");

		tow_thickness = askFloat("Please enter tow thickness in mm:  ");

		tubeValues = true;
	}

	int askInt(String thing)
	{
		Serial.print("\n\n\t");
	    Serial.print(thing);
	    String number = Serial.readStringUntil('\n');
	    printMon(number);
	    return number.toInt();
	}

	float askFloat(String thing)
	{
		Serial.print("\n\n\t");
	    Serial.print(thing);
	    String number = Serial.readStringUntil('\n');
	    printMon(number);
	    return number.toFloat();
	}

	void printMon(String thing)
	{
	 Serial.print(thing);
	}

	bool printQ (String thing)
	{
	    printMon(thing);
	    String ans = Serial.readStringUntil('\n');
	    printMon(ans);
	    printMon("\n\n");

	    char result = ans.charAt(0);
	    if(result == 'y' || result == 'Y')
	    {
	      return true;
	    }
	    else
	    {
	      return false;
	    }
	}

	void printLogo()
	{
		printMon("**************************************************************************\n");
		printMon("**************************************************************************\n");
		printMon("**********  000               000     00000          0000000    **********\n");
		printMon("**********   000             000    000   000       000    000  **********\n");
		printMon("**********    000     0     000    000     000      000         **********\n");
		printMon("**********     000   000   000     000     000       0000000    **********\n");
		printMon("**********      000 00000 000      000     000             000  **********\n");
		printMon("**********       00000 00000   88   000   000   88  000    000  **********\n");
		printMon("**********        000   000    88     00000     88    0000000   **********\n");
		printMon("**************************************************************************\n");
		printMon("************************* WinderOS Version 1.0 written by: Nathan Krupla**\n\n\n");
	}

	void build()
	{
	  int num_passes = totPass()*2;
	  current_Layer = 0;

	  while (current_Layer < tot_layers)
	  {
	      if(current_Layer % 2 == 1)
	      {
	        pass_evenLayer();
	        current_Layer++;
	      }

	    else
	    {

	        for (int i = 0; i<=num_passes; i++)
	        {
	          if (i % 2 == 1)
	                   {
	            pass_oddLayer(true);      // Start pass from home position
	            printMon("Pass number" + String(i+1) + "\n");
	          }

	          else
	          {
	            pass_oddLayer(false);      // Start pass from far side of Mandrell
	            printMon("Pass number" + String(i+1) + "\n");

	          }
	        }

	        current_Layer++;
	      }
	  }
	}

	void pass_oddLayer(boolean odd)
	{

	  long car_dest;
	  long man_dest = manDest();
	  printMon("man_dest = " + String(man_dest) + "\n");

	  //MATH

	  if (odd)
	  {
	    car_dest = carDest();
	    printMon("car_dest = " + String(car_dest) + "\n");
	  }

	  else
	  {
	    car_dest = (carDest() * -1);
	    printMon("car_dest = " + String(car_dest) + "\n");
	  }

	  float manVel = manSpeedCalc();
	  float carVel = carSpeedCalc();

	  carriage.move(car_dest);
	  carriage.setMaxSpeed(carVel);
	  carriage.setAcceleration(carVel);

	  mandrel.move(man_dest);
	  mandrel.setSpeed(manVel);
	  mandrel.setAcceleration(manVel);


	  printMon("Here We go!\n\n");
	  while(mandrel.distanceToGo() != 0)
	  {
	    //printMon("movement");
	    mandrel.run();
	    carriage.run();


	    safetyCheck();
	    stopCheck();
	  }

	  if (odd)
	  {
	    man_dest = turnAroundOffset();
	  }

	  else
	  {
	    man_dest = passOffset();
	  }

	  mandrel.move(man_dest);

	  printMon(" Moving Toward Next Carriage Pass.\n");
	    while(mandrel.distanceToGo() != 0)
	  {
	    mandrel.run();

	    safetyCheck();
	    stopCheck();
	  }
	}

	void pass_evenLayer()
	{
	  pass_oddLayer(false);

	  int carVel = (tow_width / 0.07112) * 200;
	  int car_dest = carDest();

	  int manVel = stepsManRev;
	  int man_dest = stepsManRev * (minMandrell_Length()/tow_width) + (2 * stepsManRev);

	  carriage.move(car_dest);
	  carriage.setSpeed(carVel);
	  carriage.setAcceleration(carVel);

	  mandrel.move(man_dest);
	  mandrel.setSpeed(manVel);
	  mandrel.setAcceleration(manVel);

	  while(mandrel.distanceToGo() != 0)
	  {
	    carriage.run();
	    mandrel.run();

	    safetyCheck();
	    stopCheck();
	  }

	}

	void safetyCheck()
	{
	}

	void stopCheck()
	{
	}

	void RangeHome()
	{
	}

	int totPass()     // Calculates the total number of carriage passes required for that layer.
	{
	    float other_angle = 90 - wind_angle;
	    float rad_angletwo = ((other_angle / 180)* pi);
	    float Passes = man_circ() / (tow_width * float(sin(rad_angle)) / float(sin(rad_angletwo)));
	    int numPasses = int(Passes);

	    printMon(" Number of passes calculated to be: " + String( numPasses ) + " +/- 1");

	    if (numPasses % 2 == 1)
	    {
	        return numPasses--;
	    }

	    else
	    {
	        return numPasses;
	    }
	}

	  // Calculates mandrel speed in steps/sec
	float manSpeedCalc()
	{
		float result = ( maxFeedRate * float(sin(rad_angle)) * stepsManRev) / man_circ();

	    printMon("manSpeedCalc: ");
	    printMon(String(result));
		printMon("\n");
	  	return result;
	}

	  // Calculates carriage speed in steps/Sec
	float carSpeedCalc()
	{
		float result = (maxFeedRate * float(cos(rad_angle)) * stepsCarRev) / 71.12;

	    printMon("carSpeedCalc: ");
	    printMon(String(result));
		printMon("\n");
	  	return result;
	}

	float curr_diam()
	{
		float result = man_diam + (2 * current_Layer * tow_thickness);

	    printMon("curr_diam: " + String(result) + "\n");
		return result;
	}

	float man_circ()
	{
		float result = curr_diam() * pi;

	    printMon("man_circ: " + String(result) + "\n");
		return result;
	}

	float minMandrell_Length()
	{
		float result = (tube_length + 150.0);
		//printMon(String(result));
		//printMon("\n\n");
		return result;
	}

	long carDest() // Calculates number of steps for Carriage to move during a pass.
	{

		long result = long( ( minMandrell_Length() / 71.12 ) * stepsCarRev );
	    //printMon(String( result));

	    return result;  //meters * meters per rev * steps per rev
	}

	long manDest() // returns the number of steps the mandrell must must turnduring a carriage sweep.
	{
		float other_angle = 90 - wind_angle;
	    float rad_angletwo = ((other_angle / 180)* pi);
	    long totalSteps = long(((minMandrell_Length() * float ( cos ( rad_angletwo ) ) / float ( cos ( rad_angle ) ) ) ) / man_circ() )* stepsManRev;
	    //long totalSteps = long( ( (minMandrell_Length() / cos(rad_angle) ) / man_circ() ) * stepsManRev );

		return totalSteps;
	}

	int turnAroundOffset()
	{
		int result = stepsManRev / 2;
		printMon("TurnAroundOffset = " + String(result) + "\n");
	  return result;
	}

	int passOffset()
	{
		int result = int((stepsManRev / totPass()) * (totPass()/ 2 + 1));
		printMon("PassOffset = " + String(result) + "\n");
		return result;
	}
