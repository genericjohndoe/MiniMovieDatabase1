# MiniMovieDatabase1
Android Nanodegree Project: Popular Movies Stage 1

Originally, this applet was accepted by the udacity nanodegree program the first time I put in a submission. The UI looked a little rough 
still, the networking code was verbose, and there were parts of the code that could have been easily removed or replaced with something 
else. For the first commit after Udacity accepted the applet, I decided I was going to try my hand at optimizing the code. The first thing
I did was run Lint, which identifies and correct problems with the structural quality of your code without your having to execute the app 
or write test cases. I complied with many of the changes suggested. I made the code more concise and added comments on all nonstandard 
callbacks/functions not found in the Android framework. I replaced the gridview with a recyclerview and in my opinion the layout looks alot 
better. The grid takes up the entire width of the screen now. I also implemented the use of constraint layout in the movie details activity.
I set the title of the activity to be the name of the movie selected from the grid and palyed around with the font and text sizes. The UI
looks alot better now. In order to make the netowrking code more concise, I used a third party library (OkHTTP). A custom toolbar was 
implemented in the main activity. Note: in order for the custom toolbar to be implemented the style of the activity had to be one that
did not have a toolbar/actionbar. A memory leak was removed. Originally, the adapter for the gridview (now recyclerview) took a Context 
object in the constructor, was declared static, and used in multiple classes. In order to remove the leak, an interface was created 
and implemented by the MainFragment. The interface contained one method whoes sole purpose was to feed the list of movie objects to the 
adapter so that it could be shown in the grid. The constructor of the asynctask took an interface object as the parameter. When the 
asyntask object was constructed, the fragment was used as a parameter and set to a private interface variable, the interface method
was defined in the fragment class but called in onPostExecute(), this eliminated the memory leak.

Future changes
- intro animation for grid elements
- shared element transition between activities
- reentry animation for mainfragment

Topics still misunderstood
- when rotating the screen, the number of rows remains constant, but the cells don't keep the same aspect ratio, why?
- why does passing a Context object in the constructor of a asynctask object not result in a memory leak, but using a static object
that takes in a Context as a parameter does? 
