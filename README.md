# shiftRegister
With this application you can model linear-feedback shift register that allows to generate pseudo-random numbers.
For calculation of the next state (binary code of pseudo-random number) application use matrix equation that depends on the previous state.

First of all users should choose polynomial that will describe the structure of the shift register. Polynomials stored in DB in following format:
j - exponent 
oct - octal representation of polynomials
letter - and last letter is means that it's primitive polynomial) 

After choosing of polynomial user can model the shift in a manual or auto mode, after reaching period (when initial state will be equal to current) text report will be created with all information about modeling.

More information you can find here: https://en.wikipedia.org/wiki/Linear-feedback_shift_register
