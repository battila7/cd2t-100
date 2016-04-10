package hu.progtech.cd2t100.computation;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

import hu.progtech.cd2t100.asm.InstructionElement;
import hu.progtech.cd2t100.asm.CodeElementSet;
import hu.progtech.cd2t100.asm.LineNumberedException;

import hu.progtech.cd2t100.formal.InstructionInfo;
import hu.progtech.cd2t100.formal.FormalCall;
import hu.progtech.cd2t100.formal.ParameterType;

import hu.progtech.cd2t100.computation.io.Register;
import hu.progtech.cd2t100.computation.io.CommunicationPort;

public final class InstructionFactory {
	private final InstructionRegistry instructionRegistry;

	private final ArgumentMatcher argumentMatcher;

	private final Map<String, CommunicationPort> readablePortMap;

	private final ArrayList<LineNumberedException> exceptionList;
	private final ArrayList<Instruction> instructions;

	public InstructionFactory(InstructionRegistry instructionRegistry,
														Map<String, Register> registerMap,
														Map<String, CommunicationPort> readablePortMap,
														Map<String, CommunicationPort> writeablePortMap)
	{
		this.instructionRegistry = instructionRegistry;
		this.readablePortMap = readablePortMap;

		exceptionList = new ArrayList<>();
		instructions = new ArrayList<>();

		argumentMatcher = new ArgumentMatcher(registerMap.keySet(),
																					readablePortMap.keySet(),
																					writeablePortMap.keySet());
	}

	public List<LineNumberedException> makeInstructions(CodeElementSet elementSet) {
		argumentMatcher.setLabels(elementSet.getLabelMap().keySet());

		elementSet.getInstructionList()
							.stream()
							.forEach(x -> constructInstruction(x));

		return exceptionList;
	}

	public List<Instruction> getInstructions() {
		return instructions;
	}

	private void constructInstruction(InstructionElement element) {
		InstructionInfo info =
			instructionRegistry.getInstructionInfoFor(element.getOpcode());

		if (info == null) {
			exceptionList.add(new UnknownOpcodeException(element.getLocation(),
																									 element.getOpcode()));

			return;
		}

		argumentMatcher.setInstructionElement(element);

		argumentMatcher.setInstructionInfo(info);

		try {
			argumentMatcher.match();

			List<Argument> args = argumentMatcher.getActualArguments();

			FormalCall matchedCall = argumentMatcher.getMatchedCall();

			Set<CommunicationPort> readDependencies =
					args.stream()
							.filter(x -> x.getParameterType() == ParameterType.READ_PORT)
							.map(x -> readablePortMap.get(x.getValue()))
							.collect(Collectors.toCollection(HashSet::new));

			instructions.add(new Instruction(matchedCall.getBackingMethod(),
																			 args, readDependencies,
																			 element.getLocation()));

		} catch (ArgumentMatchingException ame) {
			exceptionList.add(ame);
		}
	}
}
