# Q-learning implemetation 

## What Is Q-Learning?
Q-Learning is a Reinforcement learning policy that will find the next best action, given a current state. It chooses this action at random and aims to maximize the reward.
https://www.simplilearn.com/ice9/free_resources_article_thumb/3-components-q.JPG

```
     Step 0) Episode Initialization : 
             The agent starts episode in the bottom left corner

     => Repeat (for each state) :
     Step 1) Agent chooses an action
             Selects random action with probability ϵ or with probability 1−ϵ selects optimal action
     Step 2) Agent takes an action :
             Agent receives a reward signal from the environment and enters the next states
     Step 3) Update Q value :
             Q(s,a) = Q(s,a) + ALPHA ( r + γ max[Q(s,a)] − Q(s,a) )
```

## Demo :
### (1) sequential

<div align="center">
       <p>
       <sup>  <strong>Video -</strong> Q-learning sequential</sup>
       </p>
</div>

### (2) using MAS

<div align="center">
       <p>
       <sup>  <strong>Video -</strong> Q-learning using MAS</sup>
       </p>
</div>

<br>

<kbd>Enjoy Code</kbd> 👨‍💻
