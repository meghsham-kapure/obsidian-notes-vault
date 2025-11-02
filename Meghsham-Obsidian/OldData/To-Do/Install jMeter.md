Here's the step-by-step guide specifically for Ubuntu to install Apache JMeter:

---

### Step 1: Install Java

First, you need to have Java installed. To check if Java is already installed, run the following command:

```bash
java --version
```

If Java is not installed, follow these steps to install it:

```bash
sudo apt update
sudo apt install default-jdk
```

Once installed, verify the installation again with:

```bash
java --version
```

---

### Step 2: Download Apache JMeter

1. Visit the official Apache JMeter download page: [Download JMeter](https://jmeter.apache.org/download_jmeter.cgi).
    
2. Download the `.tgz` file for the latest version (e.g., `apache-jmeter-5.6.3.tgz`).
    

Alternatively, use `wget` to download directly to your machine:

```bash
wget https://downloads.apache.org/jmeter/binaries/apache-jmeter-5.6.3.tgz
```

---

### Step 3: Extract the JMeter Archive

Once the download is complete, extract the `.tgz` file with the following command:

```bash
tar -xvzf apache-jmeter-5.6.3.tgz
```

---

### Step 4: Move the Extracted Folder to `/opt` Directory

To make JMeter available system-wide, move the extracted folder to `/opt`:

```bash
sudo mv apache-jmeter-5.6.3 /opt/jmeter
```

You may need to enter your password to confirm the action.

---

### Step 5: Set the Environment Variables

To use JMeter from anywhere in the terminal, you need to set the path in your shell configuration file.

1. Get the full path to the JMeter directory:
    
    ```bash
    pwd
    ```
    
    This will return a path like `/opt/jmeter/apache-jmeter-5.6.3`.
    
2. Open your `.bashrc` file in a text editor:
    
    ```bash
    nano ~/.bashrc
    ```
    
3. Add the following line to the end of the `.bashrc` file (make sure to replace `/opt/jmeter/apache-jmeter-5.6.3` with the actual path you got from the `pwd` command):
    
    ```bash
    export PATH=$PATH:/opt/jmeter/apache-jmeter-5.6.3/bin
    ```
    
4. Save the file and exit the editor by pressing `CTRL+O`, `ENTER`, and then `CTRL+X`.
    

---

### Step 6: Reload the Profile

To apply the changes made to `.bashrc`, reload the profile with:

```bash
source ~/.bashrc
```

---

### Step 7: Verify JMeter Installation

Finally, verify that JMeter is installed correctly by running:

```bash
jmeter
```

This should launch the JMeter GUI if everything is set up properly.

---

### Done!

You have successfully installed Apache JMeter on Ubuntu and set up the environment to run JMeter from the terminal.