namespace CS312Project1
{
    partial class theOneAndOnlyForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.solve = new System.Windows.Forms.Button();
            this.output = new System.Windows.Forms.TextBox();
            this.Input = new System.Windows.Forms.TextBox();
            this.kInput = new System.Windows.Forms.TextBox();
            this.inputLabel = new System.Windows.Forms.Label();
            this.kValueLabel = new System.Windows.Forms.Label();
            this.outputLabel = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // solve
            // 
            this.solve.Location = new System.Drawing.Point(28, 189);
            this.solve.Name = "solve";
            this.solve.Size = new System.Drawing.Size(230, 42);
            this.solve.TabIndex = 0;
            this.solve.Text = "solve";
            this.solve.UseVisualStyleBackColor = true;
            this.solve.Click += new System.EventHandler(this.solve_Click);
            // 
            // output
            // 
            this.output.Location = new System.Drawing.Point(28, 147);
            this.output.Name = "output";
            this.output.ReadOnly = true;
            this.output.Size = new System.Drawing.Size(230, 20);
            this.output.TabIndex = 1;
            // 
            // Input
            // 
            this.Input.Location = new System.Drawing.Point(28, 50);
            this.Input.Name = "Input";
            this.Input.Size = new System.Drawing.Size(230, 20);
            this.Input.TabIndex = 2;
            this.Input.TextChanged += new System.EventHandler(this.input_TextChanged);
            // 
            // kInput
            // 
            this.kInput.Location = new System.Drawing.Point(28, 99);
            this.kInput.Name = "kInput";
            this.kInput.Size = new System.Drawing.Size(100, 20);
            this.kInput.TabIndex = 3;
            this.kInput.TextChanged += new System.EventHandler(this.k_TextChanged);
            // 
            // inputLabel
            // 
            this.inputLabel.AutoSize = true;
            this.inputLabel.Location = new System.Drawing.Point(25, 34);
            this.inputLabel.Name = "inputLabel";
            this.inputLabel.Size = new System.Drawing.Size(34, 13);
            this.inputLabel.TabIndex = 4;
            this.inputLabel.Text = "Input:";
            // 
            // kValueLabel
            // 
            this.kValueLabel.AutoSize = true;
            this.kValueLabel.Location = new System.Drawing.Point(25, 83);
            this.kValueLabel.Name = "kValueLabel";
            this.kValueLabel.Size = new System.Drawing.Size(47, 13);
            this.kValueLabel.TabIndex = 5;
            this.kValueLabel.Text = "K Value:";
            // 
            // outputLabel
            // 
            this.outputLabel.AutoSize = true;
            this.outputLabel.Location = new System.Drawing.Point(25, 131);
            this.outputLabel.Name = "outputLabel";
            this.outputLabel.Size = new System.Drawing.Size(42, 13);
            this.outputLabel.TabIndex = 6;
            this.outputLabel.Text = "Output:";
            // 
            // theOneAndOnlyForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(284, 261);
            this.Controls.Add(this.outputLabel);
            this.Controls.Add(this.kValueLabel);
            this.Controls.Add(this.inputLabel);
            this.Controls.Add(this.kInput);
            this.Controls.Add(this.Input);
            this.Controls.Add(this.output);
            this.Controls.Add(this.solve);
            this.Name = "theOneAndOnlyForm";
            this.Text = "Prime Thingy";
            this.Load += new System.EventHandler(this.theOneAndOnlyForm_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button solve;
        private System.Windows.Forms.TextBox output;
        private System.Windows.Forms.TextBox Input;
        private System.Windows.Forms.TextBox kInput;
        private System.Windows.Forms.Label inputLabel;
        private System.Windows.Forms.Label kValueLabel;
        private System.Windows.Forms.Label outputLabel;
    }
}

