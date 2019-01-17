# -*- coding: utf-8 -*-
"""
Spyder Editor

Markov Decision Process:
We have a [4x3] matrix. For programming, we take the first element as 0,0.
terminal_states : 3 1 -1 , 3 2 +1
transition.csv contains tuple (state, action, result-state, probability)
reward.csv contains tuple (state, reward)
gamma is the discount factor; basically specifies how impatient the agent is
epsilon is the max error in utility
Markov Decision Process (MDP) Toolbox:
The MDP toolbox provides classes and functions for the resolution of 
discrete-time Markov Decision Processes.
        
"""
#gamma is the discount factor
#epsilion is the maximum error allowed in the utility of any state


import sys    #System-specific parameters and functions
import csv    #The csv module implements classes to read and write tabular data in CSV format


Transitions = {}    #creates an empty dictionary; indexed by keys
Reward = {}         #creates an empty dictionary; indexed by keys

#READ TRANSISTIONS AND REWARD FILES
"""
open files as a CSV file
 2 parts- transistions_1 which contains all the transitions for each state depending
 on the direction moved
 rewards.csv contains all the rewards (-0,04 for non-terminal states and 1/-1 for terminal state)
 for (1,1), the reward is none because there is a block in that
"""
def read_allfiles():   
    #read transitions from file 
    with open('transitions_1.csv', 'r') as csvfile:
        reader = csv.reader(csvfile, delimiter=',')  #read file
        for row in reader:
            if row[0] in Transitions:   
                #append Transistions dictionary
                if row[1] in Transitions[row[0]]:
                    Transitions[row[0]][row[1]].append((float(row[3]), row[2]))
                   
                else:
                    Transitions[row[0]][row[1]] = [(float(row[3]), row[2])]
            else:
                Transitions[row[0]] = {row[1]:[(float(row[3]),row[2])]}

    #read rewards file 
    with open('rewards.csv', 'r') as csvfile:
        reader = csv.reader(csvfile, delimiter=',')
        for row in reader:  
            #append Reward dictionary
            Reward[row[0]] = float(row[1]) if row[1] != 'None' else None

read_allfiles()

#GAMMA
if len(sys.argv)>1:
    gamma = float(sys.argv[1])
else:
   gamma = 1.0

#EPSILION
if len(sys.argv)>2:
   epsilon = float(sys.argv[2])
else:
   epsilon = 0.001
   

#MDP CLASS: 
class Markov:

    """A Markov Decision Process, defined by an states, actions, transition model and reward function."""

    def __init__(self, transition={}, reward={}, gamma=0.9):
        #collect all nodes from the transition models
        self.states = transition.keys()
        #initialize transition
        self.transition = transition
        #initialize reward
        self.reward = reward
        #initialize gamma
        self.gamma = gamma

    def Rew(self, state):
        """return reward for this state."""
        return self.reward[state]

    def actions(self, state):
        """return set of actions that can be performed in this state"""
        return self.transition[state].keys()

    def Trans(self, state, action):
        """for a state and an action, return a list of (probability, result-state) pairs."""
        return self.transition[state][action]

#Initialize the MarkovDecisionProcess object
mdp_output = Markov(transition=Transitions, reward=Reward)

def value_iteration():
    """
    Solving the MDP by value iteration.
    returns utility values for states after convergence
    """
    i=-1
    Trans = mdp_output.Trans
    Rew = mdp_output.Rew
    states = mdp_output.states
    actions = mdp_output.actions

    #initialize state value to 0
    Value1 = {s: 0 for s in states}
    while True:
        
        Value = Value1.copy()
        delta = 0
        i=i+1
        print ('\n')
        print ('Iteration', i)
       
    
        #for s in states:
        for s in states:
            #update the utility values using Bellman equation
            Value1[s] = (Rew(s) + gamma * max([ sum([p * Value[s1] for (p, s1) in Trans(s, a)]) for a in actions(s)]))
            #expected sum of rewards= current reward + (expected sum of rewards) after best move
    
            print (s, '-', Value1[s])
      
            #calculate maximum difference in value
            delta = max(delta, abs(Value1[s] - Value[s]))
        
    
            

        #check if values converged then return V
        if delta <= epsilon * (1 - gamma) / gamma:
            return Value



#call value iteration
Value = value_iteration()
print ('\n', 'OUTPUT', '\n')
c=[]
b=[]
a=[]

c.append(Value['(0 2)'])
c.append(Value['(1 2)'])
c.append(Value['(2 2)'])
c.append(Value['(3 2)'])
b.append(Value['(0 1)'])
b.append('----')
b.append(Value['(2 1)'])
b.append(Value['(3 1)'])
a.append(Value['(0 0)'])
a.append(Value['(1 0)'])
a.append(Value['(2 1)'])
a.append(Value['(3 0)'])

print(c)
print(b)
print (a)

