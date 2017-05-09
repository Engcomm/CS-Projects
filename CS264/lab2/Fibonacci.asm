# Who: Junda Lou
# What: Fibonacci.asm
# Why: A program to calculate the nth Fibonacci number
# When: 5.12.2017
# How: Iterative loop

.data
Prompt: .asciiz "Please enter the integer n to be the nth Fibonacci number: "
Result:	.asciiz "The nth Fibonacci number is: "
Portion: .asciiz "Portion of the sequence is: "
Etc:	.asciiz "......"
whiteSpace: .asciiz " "
newLine: .asciiz "\n"
error:	.asciiz "Error!\n"
	
.align 2
list:	.word 10000000000

.text
.globl main

main:
	la $a0, Prompt
	li $v0, 4
	syscall
	li $v0, 5
	syscall
	move $t0, $v0

	beq $t0, $zero, Error
	
	li $t1, 0
	li $t2, 1
	li $t3, 0
	li $t4, 1
	la $t5, list
	sw $t2, 0($t5)
	addiu $t5, $t5, 4	 #initialize with 1
	beq $t4, $t0, ShowResult    # n=1, goto showResult directly
	
Loop:
	addu $t3, $t1, $t2
	move $t1, $t2
	move $t2, $t3
	addiu $t4, $t4, 1
	sw $t3, 0($t5)
	addiu $t5, $t5, 4
	bne $t4, $t0, Loop  #store in the array

ShowResult:
	la $a0, Result
	li $v0, 4
	syscall
	
	li $v0, 1
	addiu $t5, $t5, -4     # answer is 1 position back
	lw $a0, 0($t5)
	#move $a0, $t1
	syscall
	
	la $a0, newLine
	li $v0, 4
	syscall

ShowPortion:
	la $a0, Portion
	li $v0, 4
	syscall
	li $t1, 1
	blt $t0, 5, GetIndex   # n<4
	la $a0, Etc
	li $v0, 4
	syscall
	addiu $t5, $t5, -16  # move 4 more position back

ShowArray2:
	lw $a0, 0($t5)
	li $v0, 1
	syscall
	addiu $t5, $t5, 4
	addiu $t1, $t1, 1
	la $a0, whiteSpace
	li $v0, 4
	syscall
	bne $t1, 6, ShowArray2
	j End

GetIndex:
	beq $t0, 1, Clear
	addiu $t5, $t5, -4
	addiu $t1, $t1, 1        # (n-1)*4 more bits back
	bne $t1, $t0, GetIndex   

Clear:
	li $t1, 0

ShowArray1:	
	lw $a0, 0($t5)
	li $v0, 1
	syscall
	la $a0, whiteSpace
	li $v0, 4
	syscall
	addiu $t5, $t5, 4
	addiu $t1, $t1, 1
	bne $t1, $t0, ShowArray1
	j End
	
Error:
	la $a0, error
	li $v0, 4
	syscall
	j End

End:
	li $v0, 10
	syscall
