# Q-learning implemetation 

## What Is Q-Learning?
Q-Learning is a Reinforcement learning policy that will find the next best action, given a current state. It chooses this action at random and aims to maximize the reward. <br>
![image](https://github.com/Ayoub-etoullali/Activites-Pratiques-SMA-And-IA-Distribuee/assets/92756846/ddda2d6f-922a-4ef5-bd6f-d911068ab964)

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
## Example :
![image](https://github.com/Ayoub-etoullali/Activites-Pratiques-SMA-And-IA-Distribuee/assets/92756846/29825fe0-3627-4d18-aa18-2392e722d781)

![image](https://github.com/Ayoub-etoullali/Activites-Pratiques-SMA-And-IA-Distribuee/assets/92756846/65ade517-484d-4a6c-a85e-f45d60cd2a24)

![image](https://github.com/Ayoub-etoullali/Activites-Pratiques-SMA-And-IA-Distribuee/assets/92756846/fe6d60a5-d216-4a16-b386-ca34c409e051)

## Demo :
### (1) sequential
https://github.com/Ayoub-etoullali/Activites-Pratiques-SMA-And-IA-Distribuee/assets/92756846/e2125a78-bf1f-4722-b578-1d515c136931

<div align="center">
       <p>
       <sup>  <strong>Video -</strong> Q-learning sequential</sup>
       </p>
</div>

### (2) using MAS
https://drive.google.com/file/d/19oPislh6lYxo6iPLik7xPYX6YRjBlqNu/view?usp=drivesdk

##### Console
![image](https://github.com/Ayoub-etoullali/Activites-Pratiques-SMA-And-IA-Distribuee/assets/92756846/6ab705d0-9dc0-4397-a654-ad2b7dcef732)
##### JavaFX
![image](https://github.com/Ayoub-etoullali/Activites-Pratiques-SMA-And-IA-Distribuee/assets/92756846/a2567a9f-b102-4564-85b2-a3d57993c52d)

<br>

<kbd>Enjoy Code</kbd> 👨‍💻
